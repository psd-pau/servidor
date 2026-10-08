[CmdletBinding()]
param(
    [string]$Entrada,
    [string]$Sortida,
    [string]$LibreOffice = 'C:/Program Files/LibreOffice/program/soffice.com',
    [switch]$Sobreescriu
)
# Generador específic dels apunts: Open XML editable, sense Python.
# Sobreescriu només després de revisar possibles edicions manuals del DOCX.
$ErrorActionPreference = 'Stop'
if (-not $Entrada) { $Entrada = Join-Path $PSScriptRoot '../introduccio-a-spring.md' }
if (-not $Sortida) { $Sortida = Join-Path $PSScriptRoot '..' }
Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem
Add-Type -AssemblyName System.Drawing
$fonts = (New-Object System.Drawing.Text.InstalledFontCollection).Families.Name
foreach ($font in @('Arial','Poppins SemiBold','DejaVu Sans Mono')) {
    if ($font -notin $fonts) { throw "Falta la font $font; revisa la substitució abans de generar." }
}
$Entrada = [IO.Path]::GetFullPath($Entrada)
$Sortida = [IO.Path]::GetFullPath($Sortida)
$base = [IO.Path]::GetFileNameWithoutExtension($Entrada)
$docxPath = Join-Path $Sortida "$base.docx"
$pdfPath = Join-Path $Sortida "$base.pdf"
if (-not $Sobreescriu -and ((Test-Path -LiteralPath $docxPath) -or (Test-Path -LiteralPath $pdfPath))) {
    throw 'Ja hi ha documents de sortida. Revisa les edicions manuals abans d''emprar -Sobreescriu.'
}
if (-not (Test-Path -LiteralPath $LibreOffice)) { throw 'No es troba LibreOffice.' }
$logo = [IO.Path]::GetFullPath((Join-Path $PSScriptRoot '../../../../skills/documents-alumnat-pau-casesnoves/assets/logo-pau-casesnoves.png'))
if (-not (Test-Path -LiteralPath $logo)) { throw "No es troba el logotip: $logo" }
$text = [IO.File]::ReadAllText($Entrada)
if ($text -notmatch '(?m)^status: "Revisat"\r?$') { throw 'El Markdown ha d''estar marcat com a Revisat.' }
$text = [regex]::Replace($text, '\A---\r?\n.*?\r?\n---\r?\n', '', [Text.RegularExpressions.RegexOptions]::Singleline)
$lines = $text -split '\r?\n'
# Precalcula els marcadors per enllacar l'index als mateixos titols del cos.
$mainBookmarks = @{}
$scanHeadingId = 0
$scanCode = $false
foreach ($line in $lines) {
    if ($line -match '^```') { $scanCode = -not $scanCode; continue }
    if (-not $scanCode -and $line -match '^(#{1,4}) (.+)$') {
        $scanHeadingId++
        if ($line -match '^## (\d+)\. ') { $mainBookmarks[$Matches[1]] = 'section'+$scanHeadingId }
    }
}
$w = 'http://schemas.openxmlformats.org/wordprocessingml/2006/main'
$r = 'http://schemas.openxmlformats.org/officeDocument/2006/relationships'
$body = [Text.StringBuilder]::new()
$lang = '<w:lang w:val="ca-ES" w:eastAsia="ca-ES" w:bidi="ca-ES"/>'
function X([string]$s) { [Security.SecurityElement]::Escape($s) }
function Run([string]$s, [string]$pr = '') {
    '<w:r><w:rPr>' + $pr + $lang + '</w:rPr><w:t xml:space="preserve">' + (X $s) + '</w:t></w:r>'
}
function Inline([string]$s, [int]$codeSize=19) {
    $result = [Text.StringBuilder]::new()
    # Aquest Markdown empra negretes i identificadors en línia, sense hipervincles externs.
    $parts = [regex]::Split($s, '(`[^`]+`|\*\*.+?\*\*)')
    foreach ($part in $parts) {
        if ($part.StartsWith('`') -and $part.EndsWith('`')) {
            [void]$result.Append((Run $part.Substring(1,$part.Length-2) ('<w:rStyle w:val="InlineCode"/><w:sz w:val="'+$codeSize+'"/>')))
        } elseif ($part.StartsWith('**') -and $part.EndsWith('**')) {
            # Permet identificadors dins un fragment en negreta.
            $inner = $part.Substring(2,$part.Length-4)
            foreach ($piece in [regex]::Split($inner,'(`[^`]+`)')) {
                if ($piece.StartsWith('`') -and $piece.EndsWith('`')) {
                    [void]$result.Append((Run $piece.Substring(1,$piece.Length-2) ('<w:rStyle w:val="InlineCode"/><w:b/><w:sz w:val="'+$codeSize+'"/>')))
                } else { [void]$result.Append((Run $piece '<w:b/>')) }
            }
        } else { [void]$result.Append((Run $part)) }
    }
    $result.ToString()
}
function Paragraph([string]$s,[string]$style='Normal',[string]$extra='') {
    $codeSize = switch ($style) { 'Heading1' {32}; 'Heading2' {25}; 'Heading3' {21}; default {19} }
    '<w:p><w:pPr><w:pStyle w:val="' + $style + '"/>' + $extra + '</w:pPr>' + (Inline $s $codeSize) + '</w:p>'
}
function Table([string[]]$rows) {
    $cells = @($rows[0].Trim().Trim('|').Split('|'))
    $count = $cells.Count
    $keepTable = $rows.Count -le 8 -and ($rows -join '').Length -lt 2200
    $widths = switch ($count) { 2 { @(3300,6122) }; 3 { @(2500,3300,3622) }; 4 { @(1750,2500,2672,2500) }; default { @(1..$count | ForEach-Object { [int](9422/$count) }) } }
    # Les taules de rutes necessiten més espai per a les explicacions.
    if ($count -eq 3 -and $cells[0].Trim() -eq 'Ruta') { $widths = @(1700,3300,4422) }
    if ($count -eq 3 -and $cells[1] -match 'Què esperam') { $widths = @(1800,3200,4422) }
    $b = [Text.StringBuilder]::new()
    [void]$b.Append('<w:tbl><w:tblPr><w:tblW w:w="9422" w:type="dxa"/><w:tblLayout w:type="fixed"/><w:tblBorders><w:top w:val="single" w:sz="4" w:color="C9D4C9"/><w:bottom w:val="single" w:sz="4" w:color="C9D4C9"/><w:insideH w:val="single" w:sz="4" w:color="DDE4DD"/></w:tblBorders><w:tblCellMar><w:top w:w="95" w:type="dxa"/><w:left w:w="110" w:type="dxa"/><w:bottom w:w="95" w:type="dxa"/><w:right w:w="110" w:type="dxa"/></w:tblCellMar></w:tblPr><w:tblGrid>')
    foreach ($width in $widths) { [void]$b.Append('<w:gridCol w:w="' + $width + '"/>') }
    [void]$b.Append('</w:tblGrid>')
    for ($row=0;$row -lt $rows.Count;$row++) {
        if ($rows[$row] -match '^\|[\s:|\-]+\|$') { continue }
        [void]$b.Append('<w:tr><w:trPr><w:cantSplit/>')
        if ($row -eq 0) { [void]$b.Append('<w:tblHeader/>') }
        [void]$b.Append('</w:trPr>')
        $values = $rows[$row].Trim().Trim('|').Split('|')
        for ($col=0;$col -lt $count;$col++) {
            $fill = if ($row -eq 0) { '175F16' } elseif ($row % 2 -eq 0) { 'F3F5F3' } else { 'FFFFFF' }
            $style = if ($row -eq 0) { 'TableHeader' } else { 'TableText' }
            [void]$b.Append('<w:tc><w:tcPr><w:tcW w:w="' + $widths[$col] + '" w:type="dxa"/><w:shd w:fill="' + $fill + '"/><w:vAlign w:val="center"/></w:tcPr>')
            $keepRow = if ($keepTable -and $row -lt $rows.Count-1) { '<w:keepNext/>' } else { '' }
            [void]$b.Append((Paragraph $values[$col].Trim() $style $keepRow))
            [void]$b.Append('</w:tc>')
        }
        [void]$b.Append('</w:tr>')
    }
    [void]$b.Append('</w:tbl>')
    $b.ToString()
}
# Logotip incrustat amb proporcions originals, 3,6 cm d''amplada.
$img = [Drawing.Image]::FromFile($logo)
try { $cx=1296000; $cy=[int]($cx*$img.Height/$img.Width) } finally { $img.Dispose() }
[void]$body.Append(@"
<w:p><w:pPr><w:spacing w:after="200"/><w:keepNext/></w:pPr><w:r><w:drawing><wp:inline xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"><wp:extent cx="$cx" cy="$cy"/><wp:docPr id="1" name="CIFP Pau Casesnoves" descr="Logotip del CIFP Pau Casesnoves"/><a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"><a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture"><pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture"><pic:nvPicPr><pic:cNvPr id="0" name="logo-pau-casesnoves.png"/><pic:cNvPicPr/></pic:nvPicPr><pic:blipFill><a:blip r:embed="rIdLogo"/><a:stretch><a:fillRect/></a:stretch></pic:blipFill><pic:spPr><a:xfrm><a:off x="0" y="0"/><a:ext cx="$cx" cy="$cy"/></a:xfrm><a:prstGeom prst="rect"><a:avLst/></a:prstGeom></pic:spPr></pic:pic></a:graphicData></a:graphic></wp:inline></w:drawing></w:r></w:p>
"@)
$codeBlocks=0; $tables=0; $headingId=0
$script:listId=1
$script:numberInstances=[Collections.Generic.List[int]]::new()
$inIndex=$false
for ($i=0;$i -lt $lines.Count;$i++) {
    $line=$lines[$i]
    if ([string]::IsNullOrWhiteSpace($line)) { continue }
    if ($line -match '^```(.*)$') {
        $codeBlocks++
        $code = [Collections.Generic.List[string]]::new()
        $i++
        while ($i -lt $lines.Count -and $lines[$i] -notmatch '^```') { $code.Add($lines[$i]); $i++ }
        if ($i -ge $lines.Count) { throw 'Bloc de codi sense tancar.' }
        for ($j=0;$j -lt $code.Count;$j++) {
            $keep = if ($j -lt $code.Count-1) { '<w:keepNext/>' } else { '' }
            $spacing = if ($j -eq $code.Count-1) { '<w:spacing w:after="150"/>' } else { '' }
            [void]$body.Append('<w:p><w:pPr><w:pStyle w:val="Code"/>'+$keep+$spacing+'</w:pPr>'+(Run $code[$j])+'</w:p>')
        }
        continue
    }
    if ($line.StartsWith('|')) {
        $rows = [Collections.Generic.List[string]]::new()
        while ($i -lt $lines.Count -and $lines[$i].StartsWith('|')) { $rows.Add($lines[$i]); $i++ }
        $i--; $tables++
        [void]$body.Append((Table $rows.ToArray()))
        [void]$body.Append('<w:p><w:pPr><w:spacing w:after="60" w:line="40" w:lineRule="exact"/></w:pPr></w:p>')
        continue
    }
    if ($line -match '^(#{1,4}) (.+)$') {
        $level=$Matches[1].Length; $value=$Matches[2]
        $inIndex=$value -eq 'Índex'
        $style = if ($level -eq 1) { 'Title' } else { 'Heading'+($level-1) }
        $headingId++
        $pageBreak = if ($level -eq 2 -and ($inIndex -or $value -match '^\d+\. ')) { '<w:pageBreakBefore/>' } else { '' }
        $p = Paragraph $value $style $pageBreak
        $p = $p.Replace('</w:pPr>','</w:pPr><w:bookmarkStart w:id="'+$headingId+'" w:name="section'+$headingId+'"/>')
        $p = $p.Replace('</w:p>','<w:bookmarkEnd w:id="'+$headingId+'"/></w:p>')
        [void]$body.Append($p)
        continue
    }
    if ($line -match '^> (.*)$') { [void]$body.Append((Paragraph $Matches[1] 'Quote')); continue }
    if ($line -match '^- (.*)$') {
        [void]$body.Append((Paragraph $Matches[1] 'List' '<w:numPr><w:ilvl w:val="0"/><w:numId w:val="1"/></w:numPr>')); continue
    }
    if ($line -match '^(\d+)\. (.*)$') {
        $itemNumber=$Matches[1]; $itemText=$Matches[2]
        if ($inIndex) {
            if (-not $mainBookmarks.ContainsKey($itemNumber)) { throw "Entrada sense apartat: $itemNumber" }
            $linkRuns=(Inline ($itemNumber+'. '+$itemText)).Replace('<w:rPr>','<w:rPr><w:color w:val="175F16"/><w:u w:val="single"/>')
            [void]$body.Append('<w:p><w:pPr><w:pStyle w:val="List"/><w:keepLines/><w:spacing w:after="180"/></w:pPr><w:hyperlink w:anchor="'+$mainBookmarks[$itemNumber]+'" w:history="1">'+$linkRuns+'</w:hyperlink></w:p>')
            continue
        }
        # Cada llista numerada té una instància pròpia i es reinicia a 1.
        if ($itemNumber -eq '1') { $script:listId++; $script:numberInstances.Add($script:listId) }
        [void]$body.Append((Paragraph $itemText 'List' ('<w:numPr><w:ilvl w:val="0"/><w:numId w:val="'+$script:listId+'"/></w:numPr>'))); continue
    }
    $paragraph = $line.TrimEnd()
    while ($i+1 -lt $lines.Count -and -not [string]::IsNullOrWhiteSpace($lines[$i+1]) -and $lines[$i+1] -notmatch '^(#|\||```|>|- |\d+\. )' -and -not $lines[$i].EndsWith('  ')) {
        $i++; $paragraph += ' ' + $lines[$i].Trim()
    }
    [void]$body.Append((Paragraph $paragraph))
}
$sect = '<w:sectPr><w:headerReference w:type="default" r:id="rIdHeader"/><w:footerReference w:type="default" r:id="rIdFooter"/><w:pgSz w:w="11906" w:h="16838"/><w:pgMar w:top="1247" w:right="1242" w:bottom="1247" w:left="1242" w:header="570" w:footer="570"/></w:sectPr>'
$document = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><w:document xmlns:w="'+$w+'" xmlns:r="'+$r+'"><w:body>'+$body.ToString()+$sect+'</w:body></w:document>'
$styles = @"
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:styles xmlns:w="$w">
<w:docDefaults><w:rPrDefault><w:rPr><w:rFonts w:ascii="Arial" w:hAnsi="Arial" w:cs="Arial"/><w:color w:val="202124"/><w:sz w:val="22"/>$lang</w:rPr></w:rPrDefault><w:pPrDefault><w:pPr><w:widowControl/><w:spacing w:after="120" w:line="276" w:lineRule="auto"/></w:pPr></w:pPrDefault></w:docDefaults>
<w:style w:type="paragraph" w:default="1" w:styleId="Normal"><w:name w:val="Normal"/><w:qFormat/></w:style>
<w:style w:type="paragraph" w:styleId="Title"><w:name w:val="Title"/><w:basedOn w:val="Normal"/><w:next w:val="Normal"/><w:qFormat/><w:pPr><w:keepNext/><w:spacing w:before="100" w:after="220"/></w:pPr><w:rPr><w:rFonts w:ascii="Poppins SemiBold" w:hAnsi="Poppins SemiBold"/><w:color w:val="175F16"/><w:sz w:val="50"/>$lang</w:rPr></w:style>
<w:style w:type="paragraph" w:styleId="Heading1"><w:name w:val="heading 1"/><w:basedOn w:val="Normal"/><w:next w:val="Normal"/><w:qFormat/><w:pPr><w:keepNext/><w:keepLines/><w:spacing w:before="320" w:after="150"/><w:outlineLvl w:val="0"/></w:pPr><w:rPr><w:rFonts w:ascii="Poppins SemiBold" w:hAnsi="Poppins SemiBold"/><w:color w:val="175F16"/><w:sz w:val="36"/>$lang</w:rPr></w:style>
<w:style w:type="paragraph" w:styleId="Heading2"><w:name w:val="heading 2"/><w:basedOn w:val="Normal"/><w:next w:val="Normal"/><w:qFormat/><w:pPr><w:keepNext/><w:keepLines/><w:spacing w:before="250" w:after="120"/><w:outlineLvl w:val="1"/></w:pPr><w:rPr><w:rFonts w:ascii="Poppins SemiBold" w:hAnsi="Poppins SemiBold"/><w:color w:val="175F16"/><w:sz w:val="28"/>$lang</w:rPr></w:style>
<w:style w:type="paragraph" w:styleId="Heading3"><w:name w:val="heading 3"/><w:basedOn w:val="Normal"/><w:next w:val="Normal"/><w:qFormat/><w:pPr><w:keepNext/><w:keepLines/><w:spacing w:before="200" w:after="100"/><w:outlineLvl w:val="2"/></w:pPr><w:rPr><w:rFonts w:ascii="Poppins SemiBold" w:hAnsi="Poppins SemiBold"/><w:color w:val="175F16"/><w:sz w:val="23"/>$lang</w:rPr></w:style>
<w:style w:type="character" w:styleId="InlineCode"><w:name w:val="Codi en línia"/><w:rPr><w:rFonts w:ascii="DejaVu Sans Mono" w:hAnsi="DejaVu Sans Mono"/><w:sz w:val="19"/>$lang</w:rPr></w:style>
<w:style w:type="paragraph" w:styleId="Code"><w:name w:val="Codi"/><w:basedOn w:val="Normal"/><w:pPr><w:keepLines/><w:spacing w:before="0" w:after="0" w:line="210" w:lineRule="exact"/><w:ind w:left="140" w:right="140"/><w:shd w:fill="F3F5F3"/></w:pPr><w:rPr><w:rFonts w:ascii="DejaVu Sans Mono" w:hAnsi="DejaVu Sans Mono"/><w:sz w:val="18"/>$lang</w:rPr></w:style>
<w:style w:type="paragraph" w:styleId="List"><w:name w:val="Llista"/><w:basedOn w:val="Normal"/><w:pPr><w:spacing w:after="90"/></w:pPr></w:style>
<w:style w:type="paragraph" w:styleId="Quote"><w:name w:val="Idea clau"/><w:basedOn w:val="Normal"/><w:pPr><w:keepLines/><w:ind w:left="200" w:right="160"/><w:pBdr><w:left w:val="single" w:sz="18" w:space="8" w:color="175F16"/></w:pBdr><w:shd w:fill="F3F5F3"/></w:pPr></w:style>
<w:style w:type="paragraph" w:styleId="TableText"><w:name w:val="Text de taula"/><w:basedOn w:val="Normal"/><w:pPr><w:spacing w:after="40" w:line="252" w:lineRule="auto"/></w:pPr><w:rPr><w:sz w:val="20"/>$lang</w:rPr></w:style>
<w:style w:type="paragraph" w:styleId="TableHeader"><w:name w:val="Capçalera de taula"/><w:basedOn w:val="TableText"/><w:pPr><w:keepNext/></w:pPr><w:rPr><w:b/><w:color w:val="FFFFFF"/>$lang</w:rPr></w:style>
<w:style w:type="paragraph" w:styleId="Footer"><w:name w:val="Peu"/><w:basedOn w:val="Normal"/><w:pPr><w:spacing w:after="0"/><w:tabs><w:tab w:val="right" w:pos="9422"/></w:tabs></w:pPr><w:rPr><w:color w:val="595959"/><w:sz w:val="17"/>$lang</w:rPr></w:style>
</w:styles>
"@
$numbering = '<w:numbering xmlns:w="'+$w+'"><w:abstractNum w:abstractNumId="0"><w:multiLevelType w:val="singleLevel"/><w:lvl w:ilvl="0"><w:start w:val="1"/><w:numFmt w:val="bullet"/><w:lvlText w:val="•"/><w:pPr><w:tabs><w:tab w:val="num" w:pos="340"/></w:tabs><w:ind w:left="340" w:hanging="260"/></w:pPr></w:lvl></w:abstractNum><w:abstractNum w:abstractNumId="1"><w:multiLevelType w:val="singleLevel"/><w:lvl w:ilvl="0"><w:start w:val="1"/><w:numFmt w:val="decimal"/><w:lvlText w:val="%1."/><w:pPr><w:tabs><w:tab w:val="num" w:pos="360"/></w:tabs><w:ind w:left="360" w:hanging="280"/></w:pPr></w:lvl></w:abstractNum><w:num w:numId="1"><w:abstractNumId w:val="0"/></w:num>'
foreach ($id in $script:numberInstances) { $numbering += '<w:num w:numId="'+$id+'"><w:abstractNumId w:val="1"/><w:lvlOverride w:ilvl="0"><w:startOverride w:val="1"/></w:lvlOverride></w:num>' }
$numbering += '</w:numbering>'
$header = '<w:hdr xmlns:w="'+$w+'">'+(Paragraph 'UT1 · Introducció a Spring' 'Footer')+'</w:hdr>'
$footer = '<w:ftr xmlns:w="'+$w+'"><w:p><w:pPr><w:pStyle w:val="Footer"/></w:pPr>'+(Run 'David Pons · CIFP Pau Casesnoves')+'<w:r><w:tab/></w:r>'+(Run 'Pàgina ')+'<w:fldSimple w:instr=" PAGE ">'+(Run '1')+'</w:fldSimple></w:p></w:ftr>'
$rels = '<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">'
$rootRels = $rels+'<Relationship Id="rId1" Type="'+$r+'/officeDocument" Target="word/document.xml"/><Relationship Id="rId2" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Target="docProps/core.xml"/></Relationships>'
$docRels = $rels
foreach ($pair in @(@('Styles','styles','styles.xml'),@('Numbering','numbering','numbering.xml'),@('Header','header','header1.xml'),@('Footer','footer','footer1.xml'),@('Logo','image','media/logo.png'),@('Settings','settings','settings.xml'))) {
    $docRels += '<Relationship Id="rId'+$pair[0]+'" Type="'+$r+'/'+$pair[1]+'" Target="'+$pair[2]+'"/>'
}
$docRels += '</Relationships>'
$types = '<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types"><Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/><Default Extension="xml" ContentType="application/xml"/><Default Extension="png" ContentType="image/png"/>'
foreach ($pair in @(@('/word/document.xml','document.main'),@('/word/styles.xml','styles'),@('/word/numbering.xml','numbering'),@('/word/header1.xml','header'),@('/word/footer1.xml','footer'),@('/word/settings.xml','settings'))) {
    $types += '<Override PartName="'+$pair[0]+'" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.'+$pair[1]+'+xml"/>'
}
$types += '<Override PartName="/docProps/core.xml" ContentType="application/vnd.openxmlformats-package.core-properties+xml"/></Types>'
$core = '<?xml version="1.0" encoding="UTF-8"?><cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:dc="http://purl.org/dc/elements/1.1/"><dc:title>UT1 — Introducció a Spring</dc:title><dc:subject>0613. Desenvolupament web en entorn servidor · IFC33C · 2026–2027</dc:subject><dc:creator>David Pons</dc:creator><cp:lastModifiedBy>David Pons</cp:lastModifiedBy><dc:language>ca-ES</dc:language><dc:description>Apunts revisats d''introducció a Spring. CIFP Pau Casesnoves.</dc:description></cp:coreProperties>'
$parts = [ordered]@{
    '[Content_Types].xml'=$types; '_rels/.rels'=$rootRels; 'word/document.xml'=$document;
    'word/styles.xml'=$styles; 'word/numbering.xml'=$numbering; 'word/_rels/document.xml.rels'=$docRels;
    'word/header1.xml'=$header; 'word/footer1.xml'=$footer; 'docProps/core.xml'=$core;
    'word/settings.xml'='<w:settings xmlns:w="'+$w+'"><w:defaultTabStop w:val="720"/><w:themeFontLang w:val="ca-ES"/></w:settings>'
}
[void][IO.Directory]::CreateDirectory($Sortida)
$stage = Join-Path ([IO.Path]::GetTempPath()) ('spring-documents-'+[guid]::NewGuid().ToString('N'))
[void][IO.Directory]::CreateDirectory($stage)
$stagedDocx=Join-Path $stage "$base.docx"
$zip = [IO.Compression.ZipFile]::Open($stagedDocx,[IO.Compression.ZipArchiveMode]::Create)
try {
    foreach ($name in $parts.Keys) {
        $xml = [xml]$parts[$name] # Comprova XML abans d''escriure.
        $entry=$zip.CreateEntry($name); $stream=$entry.Open()
        $writer=[IO.StreamWriter]::new($stream,[Text.UTF8Encoding]::new($false))
        try { $writer.Write($parts[$name]) } finally { $writer.Dispose() }
    }
    $entry=$zip.CreateEntry('word/media/logo.png'); $stream=$entry.Open(); $inputStream=[IO.File]::OpenRead($logo)
    try { $inputStream.CopyTo($stream) } finally { $inputStream.Dispose(); $stream.Dispose() }
} finally { $zip.Dispose() }
$profile = [uri](Join-Path $stage 'lo-profile')
$profileArgument='-env:UserInstallation='+$profile.AbsoluteUri
$arguments = @($profileArgument,'--headless','--convert-to','pdf:writer_pdf_Export:{"UseTaggedPDF":{"type":"boolean","value":"true"},"ExportBookmarks":{"type":"boolean","value":"true"}}','--outdir',$stage,$stagedDocx)
$start=[Diagnostics.ProcessStartInfo]::new()
$start.FileName=$LibreOffice
# .NET Framework no disposa de ProcessStartInfo.ArgumentList. Escapat de l''argv
# de Windows, sense passar per cmd.exe ni interpretar els arguments com a shell.
$start.Arguments=($arguments | ForEach-Object { '"'+[regex]::Replace([regex]::Replace($_,'(\\*)"','$1$1\"'),'(\\+)$','$1$1')+'"' }) -join ' '
$start.UseShellExecute=$false; $start.CreateNoWindow=$true
$process=[Diagnostics.Process]::Start($start)
$process.WaitForExit()
if ($process.ExitCode -ne 0) { throw "LibreOffice ha fallat: $($process.ExitCode)" }
$stagedPdf=Join-Path $stage "$base.pdf"
if (-not (Test-Path -LiteralPath $stagedPdf)) { throw "No s''ha generat el PDF. Temporals: $stage" }
Copy-Item -LiteralPath $stagedDocx -Destination $docxPath -Force:$Sobreescriu
Copy-Item -LiteralPath $stagedPdf -Destination $pdfPath -Force:$Sobreescriu
Write-Output "DOCX: $docxPath"
Write-Output "PDF: $pdfPath"
Write-Output "Blocs de codi: $codeBlocks; taules: $tables; temporals de generació: $stage"
