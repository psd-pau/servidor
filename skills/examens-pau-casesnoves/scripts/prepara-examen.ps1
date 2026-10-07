[CmdletBinding()]
param(
    [Parameter(Mandatory=$true)][ValidateRange(1,999)][int]$Unitat,
    [Parameter(Mandatory=$true)][ValidateNotNullOrEmpty()][string]$Sortida,
    [ValidateNotNullOrEmpty()][string]$Grup = 'IFC33C',
    [ValidatePattern('^\d{2}/\d{2}$')][string]$Curs,
    [datetime]$DataExamen
)
$ErrorActionPreference = 'Stop'
$Modul = 'Desenvolupament web en entorn servidor'
Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem
$templatePath = [IO.Path]::GetFullPath((Join-Path $PSScriptRoot '../assets/plantilla-examen.docx'))
$outputPath = [IO.Path]::GetFullPath($Sortida)
if ([IO.Path]::GetExtension($outputPath) -ne '.docx') { throw 'La sortida ha de ser DOCX.' }
if (Test-Path -LiteralPath $outputPath) { throw 'La sortida ja existeix; no se sobreescriurà.' }
$hasExamDate = $PSBoundParameters.ContainsKey('DataExamen')
if ($hasExamDate) { $referenceDate = $DataExamen } else {
    try { $zone = [TimeZoneInfo]::FindSystemTimeZoneById('Europe/Madrid') }
    catch { $zone = [TimeZoneInfo]::FindSystemTimeZoneById('Romance Standard Time') }
    $referenceDate = [TimeZoneInfo]::ConvertTimeFromUtc([datetime]::UtcNow, $zone)
}
if (-not $Curs) {
    $firstYear = $referenceDate.Year
    if ($referenceDate.Month -lt 9) { $firstYear-- }
    $Curs = '{0:00}/{1:00}' -f ($firstYear % 100), (($firstYear + 1) % 100)
}
$w = 'http://schemas.openxmlformats.org/wordprocessingml/2006/main'

function Read-Part($archive, [string]$name) {
    $reader = [IO.StreamReader]::new($archive.GetEntry($name).Open())
    try {
        $doc = [Xml.XmlDocument]::new()
        $doc.PreserveWhitespace = $true
        $doc.LoadXml($reader.ReadToEnd())
        return ,$doc
    } finally { $reader.Dispose() }
}
function Save-Part($archive, [string]$name, $doc) {
    $entry = $archive.GetEntry($name)
    $entry.Delete()
    $stream = $archive.CreateEntry($name).Open()
    try { $doc.Save($stream) } finally { $stream.Dispose() }
}
function Set-CellValue($cell, [string]$value, $ns) {
    $paragraph = $cell.SelectSingleNode('./w:p', $ns)
    $run = $paragraph.SelectSingleNode('./w:r', $ns)
    if (-not $run) {
        $run = $paragraph.OwnerDocument.CreateElement('w','r',$w)
        [void]$paragraph.AppendChild($run)
    }
    $properties = $run.SelectSingleNode('./w:rPr', $ns)
    if (-not $properties) {
        $properties = $paragraph.OwnerDocument.CreateElement('w','rPr',$w)
        [void]$run.PrependChild($properties)
    }
    foreach ($setting in @('rFonts','sz','szCs')) {
        $node = $properties.SelectSingleNode("./w:$setting", $ns)
        if (-not $node) {
            $node = $paragraph.OwnerDocument.CreateElement('w',$setting,$w)
            [void]$properties.AppendChild($node)
        }
        if ($setting -eq 'rFonts') {
            foreach ($attribute in @('ascii','hAnsi','eastAsia','cs')) { [void]$node.SetAttribute($attribute,$w,'Poppins') }
        } else { [void]$node.SetAttribute('val',$w,'22') }
    }
    $text = $paragraph.OwnerDocument.CreateElement('w','t',$w)
    $text.InnerText = $value
    [void]$run.AppendChild($text)
}

# Edit a memory copy; create the output only after all template checks pass.
$memory = [IO.MemoryStream]::new()
$inputStream = [IO.File]::OpenRead($templatePath)
try { $inputStream.CopyTo($memory) } finally { $inputStream.Dispose() }
$memory.Position = 0
$archive = [IO.Compression.ZipArchive]::new($memory,[IO.Compression.ZipArchiveMode]::Update,$true)
try {
    $doc = Read-Part $archive 'word/document.xml'
    $ns = [Xml.XmlNamespaceManager]::new($doc.NameTable)
    $ns.AddNamespace('w',$w)
    $titles = @($doc.SelectNodes('//w:p',$ns) | Where-Object {
        (($_.SelectNodes('.//w:t',$ns) | ForEach-Object { $_.InnerText }) -join '') -eq "PROVA D’AVALUACIÓ UNITAT X"
    })
    if ($titles.Count -ne 1) { throw 'No es troba el títol únic de la plantilla.' }
    $placeholder = $titles[0].SelectSingleNode('./w:r/w:t[text()="X"]',$ns)
    if (-not $placeholder) { throw 'No es troba el fragment X del títol.' }
    $placeholder.InnerText = [string]$Unitat
    $values = [ordered]@{ Curs=$Curs; Grup=$Grup; 'Mòdul'=$Modul }
    if ($hasExamDate) { $values['Data'] = $DataExamen.ToString('dd/MM/yyyy') }
    foreach ($label in $values.Keys) {
        $matches = @($doc.SelectNodes('//w:tc',$ns) | Where-Object {
            (($_.SelectNodes('.//w:t',$ns) | ForEach-Object { $_.InnerText }) -join '') -eq $label
        })
        if ($matches.Count -ne 1) { throw "Camp absent o ambigu: $label" }
        $target = $matches[0].SelectSingleNode('following-sibling::w:tc[1]',$ns)
        if (-not $target -or $target.SelectNodes('.//w:t',$ns).Count -gt 0) { throw "Cel·la de valor inesperada: $label" }
        Set-CellValue $target $values[$label] $ns
    }
    Save-Part $archive 'word/document.xml' $doc
    $core = Read-Part $archive 'docProps/core.xml'
    $coreNs = [Xml.XmlNamespaceManager]::new($core.NameTable)
    $coreNs.AddNamespace('dc','http://purl.org/dc/elements/1.1/')
    $creator = $core.SelectSingleNode('//dc:creator',$coreNs)
    if (-not $creator) {
        $creator = $core.CreateElement('dc','creator','http://purl.org/dc/elements/1.1/')
        [void]$core.DocumentElement.AppendChild($creator)
    }
    $creator.InnerText = 'David Pons'
    $language = $core.SelectSingleNode('//dc:language',$coreNs)
    if (-not $language) {
        $language = $core.CreateElement('dc','language','http://purl.org/dc/elements/1.1/')
        [void]$core.DocumentElement.AppendChild($language)
    }
    $language.InnerText = 'ca-ES'
    Save-Part $archive 'docProps/core.xml' $core
} finally { $archive.Dispose() }
try {
    [void][IO.Directory]::CreateDirectory([IO.Path]::GetDirectoryName($outputPath))
    $outputStream = [IO.File]::Open($outputPath,[IO.FileMode]::CreateNew,[IO.FileAccess]::Write)
    # Rebuild the ZIP: LibreOffice rejects some template archives updated in place
    # by .NET, even when their XML and compressed entries are valid.
    $memory.Position=0
    $sourceArchive=[IO.Compression.ZipArchive]::new($memory,[IO.Compression.ZipArchiveMode]::Read,$true)
    $outputArchive=[IO.Compression.ZipArchive]::new($outputStream,[IO.Compression.ZipArchiveMode]::Create,$true)
    try {
        foreach ($entry in $sourceArchive.Entries) {
            $inputEntry=$entry.Open(); $outputEntry=$outputArchive.CreateEntry($entry.FullName).Open()
            try { $inputEntry.CopyTo($outputEntry) } finally { $inputEntry.Dispose(); $outputEntry.Dispose() }
        }
    } finally { $outputArchive.Dispose(); $sourceArchive.Dispose(); $outputStream.Dispose() }
} finally { $memory.Dispose() }
Write-Output "Base d'examen creada: $outputPath (UT $Unitat, curs $Curs, grup $Grup)."
