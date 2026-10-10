import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;
import java.util.zip.*;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Generador dels apunts UT2: Markdown -> Open XML editable -> LibreOffice PDF.
 * Només JDK 17+ i LibreOffice; sense Python ni biblioteques addicionals.
 * Des de l'arrel del repositori:
 * java -Djava.awt.headless=true materials/0613-desenvolupament-web-entorn-servidor/UT2/eines/GeneraApunts.java
 * Opcions: --entrada FITXER --sortida DIRECTORI --libreoffice EXECUTABLE --sobreescriu
 * Revisau les possibles edicions manuals abans d'emprar --sobreescriu.
 */
public class GeneraApunts {
    static final String W="http://schemas.openxmlformats.org/wordprocessingml/2006/main";
    static final String R="http://schemas.openxmlformats.org/officeDocument/2006/relationships";
    static final String LANG="<w:lang w:val=\"ca-ES\" w:eastAsia=\"ca-ES\" w:bidi=\"ca-ES\"/>";
    static final int WIDTH=9412;
    static final String GREEN="175F16", INK="202124";
    static String bodyFont="Arial";
    static final Pattern HEADING=Pattern.compile("^(#{1,4}) (.+)$"), NUMBER=Pattern.compile("^(\\d+)\\. (.*)$");
    static final Pattern INLINE=Pattern.compile("(`[^`]+`|\\*\\*.+?\\*\\*|(?<!\\*)\\*[^*]+\\*|\\[([^\\]]+)\\]\\(([^)]+)\\))");
    static final Map<String,byte[]> parts=new LinkedHashMap<>();
    static final StringBuilder rels=new StringBuilder(), body=new StringBuilder();
    static final List<Integer> numbered=new ArrayList<>();
    static final List<String> expected=new ArrayList<>();
    static Path input,output,repo; static int imageId=0,linkId=0,bookmarkId=0,listId=1,tables=0,codes=0;
    static String x(String s){return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&apos;");}
    static String run(String s,String props){return "<w:r><w:rPr>"+props+LANG+"</w:rPr><w:t xml:space=\"preserve\">"+x(s)+"</w:t></w:r>";}
    static String inline(String s,String inherited,int codeSize){
        Matcher m=INLINE.matcher(s); StringBuilder b=new StringBuilder(); int start=0;
        while(m.find()){
            b.append(run(s.substring(start,m.start()),inherited)); String p=m.group();
            if(p.startsWith("`")) b.append(run(p.substring(1,p.length()-1),inherited+"<w:rStyle w:val=\"InlineCode\"/><w:sz w:val=\""+codeSize+"\"/>"));
            else if(p.startsWith("**")) b.append(inline(p.substring(2,p.length()-2),inherited+"<w:b/>",codeSize));
            else if(p.startsWith("*")) b.append(inline(p.substring(1,p.length()-1),inherited+"<w:i/>",codeSize));
            else {
                String id="external"+(++linkId);
                rels.append("<Relationship Id=\"").append(id).append("\" Type=\"").append(R).append("/hyperlink\" Target=\"").append(x(m.group(3))).append("\" TargetMode=\"External\"/>");
                b.append("<w:hyperlink r:id=\"").append(id).append("\">").append(inline(m.group(2),inherited+"<w:color w:val=\"175F16\"/><w:u w:val=\"single\"/>",codeSize)).append("</w:hyperlink>");
            }
            start=m.end();
        }
        return b.append(run(s.substring(start),inherited)).toString();
    }
    static String p(String s,String style,String extra){
        int size=switch(style){case "Title"->46;case "Heading1"->32;case "Heading2"->25;case "Heading3"->21;default->18;};
        return "<w:p><w:pPr><w:pStyle w:val=\""+style+"\"/>"+extra+"</w:pPr>"+inline(s,"",size)+"</w:p>";
    }
    static void paragraph(String s,String style,String extra){body.append(p(s,style,extra));expected.add(plain(s));}
    static String plain(String s){
        Matcher m=INLINE.matcher(s);StringBuilder b=new StringBuilder();int start=0;
        while(m.find()){b.append(s,start,m.start());String a=m.group();b.append(a.startsWith("`")?a.substring(1,a.length()-1):a.startsWith("**")?plain(a.substring(2,a.length()-2)):a.startsWith("*")?plain(a.substring(1,a.length()-1)):plain(m.group(2)));start=m.end();}
        return b.append(s.substring(start)).toString();
    }
    static String heading(String s,String style,String extra,String anchor){
        int id=++bookmarkId;
        return p(s,style,extra).replace("</w:pPr>","</w:pPr><w:bookmarkStart w:id=\""+id+"\" w:name=\""+anchor+"\"/>").replace("</w:p>","<w:bookmarkEnd w:id=\""+id+"\"/></w:p>");
    }
    static void addImage(Path path,int width,String alt)throws Exception{
        BufferedImage img=ImageIO.read(path.toFile());if(img==null)throw new IOException("Imatge invàlida: "+path);
        int id=++imageId;String target="media/image"+id+".png";parts.put("word/"+target,Files.readAllBytes(path));
        rels.append("<Relationship Id=\"img").append(id).append("\" Type=\"").append(R).append("/image\" Target=\"").append(target).append("\"/>");
        long cx=(long)width*635,cy=cx*img.getHeight()/img.getWidth();
        body.append("<w:p><w:pPr><w:jc w:val=\"center\"/><w:keepNext/><w:spacing w:before=\"100\" w:after=\"100\"/></w:pPr><w:r><w:drawing><wp:inline xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\"><wp:extent cx=\"").append(cx).append("\" cy=\"").append(cy).append("\"/><wp:docPr id=\"").append(id).append("\" name=\"Figura ").append(id).append("\" descr=\"").append(x(alt)).append("\"/><a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"><a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\"><pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\"><pic:nvPicPr><pic:cNvPr id=\"0\" name=\"").append(x(path.getFileName().toString())).append("\"/><pic:cNvPicPr/></pic:nvPicPr><pic:blipFill><a:blip r:embed=\"img").append(id).append("\"/><a:stretch><a:fillRect/></a:stretch></pic:blipFill><pic:spPr><a:xfrm><a:off x=\"0\" y=\"0\"/><a:ext cx=\"").append(cx).append("\" cy=\"").append(cy).append("\"/></a:xfrm><a:prstGeom prst=\"rect\"><a:avLst/></a:prstGeom></pic:spPr></pic:pic></a:graphicData></a:graphic></wp:inline></w:drawing></w:r></w:p>");
    }
    static List<String> cells(String line){return Arrays.stream(line.substring(1,line.lastIndexOf('|')).split("\\|",-1)).map(String::trim).toList();}
    static void table(List<String> rows){
        tables++;List<List<String>> all=new ArrayList<>();for(String row:rows)if(!row.matches("\\|[\\s:|\\-]+\\|"))all.add(cells(row));
        int n=all.get(0).size();int[] widths=switch(n){case 2->new int[]{3550,5862};case 3->new int[]{2700,3356,3356};case 4->new int[]{2353,2853,1853,2353};default->throw new IllegalArgumentException("Taula de "+n+" columnes");};
        String head=String.join(" ",all.get(0));
        if(n==2 && (head.contains("Propietat")||head.contains("Anotació o paràmetre")||head.contains("Sufix de la ruta")))widths=new int[]{4100,5312};
        if(n==3 && head.contains("Declaració d'herència"))widths=new int[]{2700,4000,2712};
        if(n==3 && head.contains("Anotació i paràmetre"))widths=new int[]{2950,3000,3462};
        body.append("<w:tbl><w:tblPr><w:tblW w:w=\"9412\" w:type=\"dxa\"/><w:tblLayout w:type=\"fixed\"/><w:tblBorders><w:top w:val=\"single\" w:sz=\"4\" w:color=\"C9D4C9\"/><w:bottom w:val=\"single\" w:sz=\"4\" w:color=\"C9D4C9\"/><w:insideH w:val=\"single\" w:sz=\"4\" w:color=\"DDE4DD\"/></w:tblBorders><w:tblCellMar><w:top w:w=\"90\" w:type=\"dxa\"/><w:left w:w=\"105\" w:type=\"dxa\"/><w:bottom w:w=\"90\" w:type=\"dxa\"/><w:right w:w=\"105\" w:type=\"dxa\"/></w:tblCellMar></w:tblPr><w:tblGrid>");
        for(int w:widths)body.append("<w:gridCol w:w=\"").append(w).append("\"/>");body.append("</w:tblGrid>");
        boolean keepTable=all.size()<=7 && String.join("",rows).length()<1400;
        for(int row=0;row<all.size();row++){
            if(all.get(row).size()!=n)throw new IllegalArgumentException("Columnes inconsistents");
            body.append("<w:tr><w:trPr><w:cantSplit/>").append(row==0?"<w:tblHeader/>":"").append("</w:trPr>");
            for(int col=0;col<n;col++){
                body.append("<w:tc><w:tcPr><w:tcW w:w=\"").append(widths[col]).append("\" w:type=\"dxa\"/><w:shd w:fill=\"").append(row==0?GREEN:row%2==0?"F3F5F3":"FFFFFF").append("\"/><w:vAlign w:val=\"center\"/></w:tcPr>");
                paragraph(all.get(row).get(col),row==0?"TableHeader":"TableText",(row==0||(keepTable&&row<all.size()-1))?"<w:keepNext/>":"");body.append("</w:tc>");
            }body.append("</w:tr>");
        }body.append("</w:tbl><w:p><w:pPr><w:spacing w:after=\"60\" w:line=\"40\" w:lineRule=\"exact\"/></w:pPr></w:p>");
    }
    static void parse(List<String> lines)throws Exception{
        boolean index=false;int main=0;
        for(int i=0;i<lines.size();i++){
            String line=lines.get(i);if(line.isBlank())continue;
            if(line.startsWith("```")){
                List<String> block=new ArrayList<>();while(++i<lines.size()&&!lines.get(i).startsWith("```"))block.add(lines.get(i));
                if(i==lines.size())throw new IllegalArgumentException("Bloc de codi obert");codes++;
                for(int k=0;k<block.size();k++){
                    String value=block.get(k);boolean keep=k<block.size()-1 && (block.size()<=24 || (!value.isBlank()&&!block.get(k+1).isBlank()));
                    // Es conserva cada línia lògica; el processador només en fa l'ajust visual.
                    body.append("<w:p><w:pPr><w:pStyle w:val=\"Code\"/>").append(keep?"<w:keepNext/>":"").append(k==block.size()-1?"<w:spacing w:after=\"150\"/>":"").append("</w:pPr>").append(run(value,"")).append("</w:p>");expected.add(value);
                }continue;
            }
            if(line.startsWith("|")){
                List<String> rows=new ArrayList<>();do{rows.add(lines.get(i++));}while(i<lines.size()&&lines.get(i).startsWith("|"));i--;table(rows);continue;
            }
            Matcher h=HEADING.matcher(line);
            if(h.matches()){
                int level=h.group(1).length();String title=h.group(2);index=title.equals("Índex");
                if(level==1){paragraph(title,"Title","");continue;}
                String style="Heading"+(level-1),extra="",anchor="sub"+(bookmarkId+1);
                if(level==2 && (index||title.matches("\\d+\\. .*"))){extra="<w:pageBreakBefore/>";if(!index){main++;anchor="apartat"+main;}}
                if(title.equals("Presentació"))style="IntroHeading";
                if(index)style="IndexHeading";
                body.append(heading(title,style,extra,anchor));expected.add(plain(title));continue;
            }
            if(line.startsWith("![")){
                Matcher im=Pattern.compile("!\\[([^]]*)\\]\\(([^)]+)\\)").matcher(line);if(!im.matches())throw new IllegalArgumentException(line);
                Path path=input.getParent().resolve(im.group(2));addImage(path,path.getFileName().toString().equals("spring-jpa-diagram.png")?4700:WIDTH,im.group(1));continue;
            }
            if(line.startsWith("*Figura ")){paragraph(line,"Caption","");continue;}
            if(line.startsWith("**CIFP")||line.startsWith("**Mòdul:")||line.startsWith("**Professor:")){
                paragraph(line.trim(),"Subtitle","");continue;
            }
            if(line.startsWith("> ")){paragraph(line.substring(2),"Quote","");continue;}
            if(line.startsWith("- ")){paragraph(line.substring(2),"List","<w:numPr><w:ilvl w:val=\"0\"/><w:numId w:val=\"1\"/></w:numPr>");continue;}
            Matcher num=NUMBER.matcher(line);
            if(num.matches()){
                if(index){
                    String anchor="apartat"+num.group(1);
                    body.append("<w:p><w:pPr><w:pStyle w:val=\"IndexEntry\"/></w:pPr><w:hyperlink w:anchor=\"").append(anchor).append("\" w:history=\"1\">").append(inline(line,"<w:color w:val=\"175F16\"/><w:u w:val=\"single\"/>",20)).append("</w:hyperlink></w:p>");expected.add(plain(line));
                }else{
                    if(num.group(1).equals("1")){listId++;numbered.add(listId);}
                    paragraph(num.group(2),"List","<w:numPr><w:ilvl w:val=\"0\"/><w:numId w:val=\""+listId+"\"/></w:numPr>");
                }continue;
            }
            StringBuilder para=new StringBuilder(line.stripTrailing());
            while(i+1<lines.size()&&!lines.get(i+1).isBlank()&&!lines.get(i+1).matches("^(#|\\||```|>|- |\\d+\\. |!\\[).*")){para.append(' ').append(lines.get(++i).trim());}
            paragraph(para.toString(),"Normal","");
        }
        if(main!=10)throw new IllegalArgumentException("S'esperen 10 apartats principals");
    }
    static String style(String id,String name,String base,String pr,String rpr){return "<w:style w:type=\"paragraph\" w:styleId=\""+id+"\"><w:name w:val=\""+name+"\"/><w:basedOn w:val=\""+base+"\"/><w:next w:val=\"Normal\"/><w:qFormat/><w:pPr>"+pr+"</w:pPr><w:rPr>"+rpr+LANG+"</w:rPr></w:style>";}
    static String styles(){
        StringBuilder s=new StringBuilder("<w:styles xmlns:w=\""+W+"\"><w:docDefaults><w:rPrDefault><w:rPr><w:rFonts w:ascii=\""+bodyFont+"\" w:hAnsi=\""+bodyFont+"\" w:cs=\""+bodyFont+"\"/><w:sz w:val=\"22\"/><w:color w:val=\"202124\"/>"+LANG+"</w:rPr></w:rPrDefault><w:pPrDefault><w:pPr><w:widowControl/><w:spacing w:after=\"120\" w:line=\"276\" w:lineRule=\"auto\"/></w:pPr></w:pPrDefault></w:docDefaults><w:style w:type=\"paragraph\" w:default=\"1\" w:styleId=\"Normal\"><w:name w:val=\"Normal\"/><w:qFormat/></w:style>");
        String bold="<w:rFonts w:ascii=\"Poppins SemiBold\" w:hAnsi=\"Poppins SemiBold\"/><w:color w:val=\"175F16\"/>";
        s.append(style("Title","Títol","Normal","<w:keepNext/><w:spacing w:before=\"180\" w:after=\"260\"/>",bold+"<w:sz w:val=\"50\"/>"));
        s.append(style("Subtitle","Subtítol","Normal","<w:keepNext/><w:spacing w:after=\"75\"/>","<w:sz w:val=\"20\"/><w:color w:val=\"595959\"/>"));
        for(int i=1;i<=3;i++)s.append(style("Heading"+i,"heading "+i,"Normal","<w:keepNext/><w:keepLines/><w:spacing w:before=\""+(i==1?280:220)+"\" w:after=\"140\"/><w:outlineLvl w:val=\""+(i-1)+"\"/>",bold+"<w:sz w:val=\""+(i==1?36:i==2?28:23)+"\"/>"));
        s.append(style("IntroHeading","Presentació","Heading1","<w:keepNext/>","<w:sz w:val=\"28\"/>"));
        s.append(style("IndexHeading","Índex","Heading1","<w:keepNext/>",""));
        s.append(style("IndexEntry","Entrada d'índex","Normal","<w:keepLines/><w:spacing w:after=\"220\"/><w:ind w:left=\"360\" w:hanging=\"360\"/>","<w:sz w:val=\"24\"/>"));
        s.append("<w:style w:type=\"character\" w:styleId=\"InlineCode\"><w:name w:val=\"Codi en línia\"/><w:rPr><w:rFonts w:ascii=\"DejaVu Sans Mono\" w:hAnsi=\"DejaVu Sans Mono\"/><w:sz w:val=\"18\"/>"+LANG+"</w:rPr></w:style>");
        s.append(style("Code","Codi","Normal","<w:keepLines/><w:spacing w:after=\"0\" w:line=\"220\" w:lineRule=\"exact\"/><w:ind w:left=\"160\" w:right=\"140\"/><w:shd w:fill=\"F3F5F3\"/>","<w:rFonts w:ascii=\"DejaVu Sans Mono\" w:hAnsi=\"DejaVu Sans Mono\"/><w:sz w:val=\"18\"/>"));
        s.append(style("List","Llista","Normal","<w:spacing w:after=\"90\"/>",""));
        s.append(style("Quote","Idea clau","Normal","<w:keepLines/><w:ind w:left=\"200\" w:right=\"160\"/><w:pBdr><w:left w:val=\"single\" w:sz=\"18\" w:space=\"8\" w:color=\"175F16\"/></w:pBdr><w:shd w:fill=\"F3F5F3\"/>",""));
        s.append(style("TableText","Text de taula","Normal","<w:spacing w:after=\"40\" w:line=\"240\" w:lineRule=\"auto\"/>","<w:sz w:val=\"20\"/>"));
        s.append(style("TableHeader","Capçalera de taula","TableText","<w:keepNext/>","<w:b/><w:color w:val=\"FFFFFF\"/>"));
        s.append(style("Caption","Llegenda","Normal","<w:keepLines/><w:spacing w:after=\"160\"/>","<w:sz w:val=\"19\"/><w:color w:val=\"595959\"/>"));
        s.append(style("Footer","Peu","Normal","<w:spacing w:after=\"0\"/><w:tabs><w:tab w:val=\"right\" w:pos=\"9412\"/></w:tabs>","<w:sz w:val=\"17\"/><w:color w:val=\"595959\"/>"));
        return s.append("</w:styles>").toString();
    }
    static String numbering(){
        StringBuilder n=new StringBuilder("<w:numbering xmlns:w=\""+W+"\">");
        for(int i=0;i<2;i++)n.append("<w:abstractNum w:abstractNumId=\"").append(i).append("\"><w:multiLevelType w:val=\"singleLevel\"/><w:lvl w:ilvl=\"0\"><w:start w:val=\"1\"/><w:numFmt w:val=\"").append(i==0?"bullet":"decimal").append("\"/><w:lvlText w:val=\"").append(i==0?"•":"%1.").append("\"/><w:pPr><w:ind w:left=\"380\" w:hanging=\"280\"/></w:pPr></w:lvl></w:abstractNum>");
        n.append("<w:num w:numId=\"1\"><w:abstractNumId w:val=\"0\"/></w:num>");
        for(int id:numbered)n.append("<w:num w:numId=\"").append(id).append("\"><w:abstractNumId w:val=\"1\"/><w:lvlOverride w:ilvl=\"0\"><w:startOverride w:val=\"1\"/></w:lvlOverride></w:num>");
        return n.append("</w:numbering>").toString();
    }
    static void part(String name,String xml){parts.put(name,("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+xml).getBytes(StandardCharsets.UTF_8));}
    static void packageDocx(Path docx)throws Exception{
        String sect="<w:sectPr><w:headerReference w:type=\"default\" r:id=\"header\"/><w:footerReference w:type=\"default\" r:id=\"footer\"/><w:pgSz w:w=\"11906\" w:h=\"16838\"/><w:pgMar w:top=\"1247\" w:right=\"1247\" w:bottom=\"1247\" w:left=\"1247\" w:header=\"570\" w:footer=\"570\"/></w:sectPr>";
        part("word/document.xml","<w:document xmlns:w=\""+W+"\" xmlns:r=\""+R+"\"><w:body>"+body+sect+"</w:body></w:document>");part("word/styles.xml",styles());part("word/numbering.xml",numbering());
        part("word/header1.xml","<w:hdr xmlns:w=\""+W+"\">"+p("UT2 · Accés a dades amb Spring","Footer","")+"</w:hdr>");
        part("word/footer1.xml","<w:ftr xmlns:w=\""+W+"\"><w:p><w:pPr><w:pStyle w:val=\"Footer\"/></w:pPr>"+run("David Pons · CIFP Pau Casesnoves","")+"<w:r><w:tab/></w:r>"+run("Pàgina ","")+"<w:fldSimple w:instr=\" PAGE \">"+run("1","")+"</w:fldSimple></w:p></w:ftr>");
        part("word/settings.xml","<w:settings xmlns:w=\""+W+"\"><w:defaultTabStop w:val=\"720\"/><w:themeFontLang w:val=\"ca-ES\"/><w:compat><w:compatSetting w:name=\"compatibilityMode\" w:uri=\"http://schemas.microsoft.com/office/word\" w:val=\"15\"/></w:compat></w:settings>");
        part("docProps/core.xml","<cp:coreProperties xmlns:cp=\"http://schemas.openxmlformats.org/package/2006/metadata/core-properties\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\"><dc:title>UT2 — Accés a dades amb Spring</dc:title><dc:subject>0613. Desenvolupament web en entorn servidor · IFC33C · 2026–2027</dc:subject><dc:creator>David Pons</dc:creator><cp:lastModifiedBy>David Pons</cp:lastModifiedBy><dc:language>ca-ES</dc:language><dc:description>Apunts d’alumnat. CIFP Pau Casesnoves. Projecte unitat2_2627_simplificat.</dc:description></cp:coreProperties>");
        String relstart="<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">";
        part("_rels/.rels",relstart+"<Relationship Id=\"main\" Type=\""+R+"/officeDocument\" Target=\"word/document.xml\"/><Relationship Id=\"core\" Type=\"http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties\" Target=\"docProps/core.xml\"/></Relationships>");
        for(String[] a:new String[][]{{"styles","styles.xml"},{"numbering","numbering.xml"},{"header","header1.xml"},{"footer","footer1.xml"},{"settings","settings.xml"}})rels.append("<Relationship Id=\"").append(a[0]).append("\" Type=\"").append(R).append('/').append(a[0]).append("\" Target=\"").append(a[1]).append("\"/>");
        part("word/_rels/document.xml.rels",relstart+rels+"</Relationships>");
        StringBuilder types=new StringBuilder("<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\"><Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/><Default Extension=\"xml\" ContentType=\"application/xml\"/><Default Extension=\"png\" ContentType=\"image/png\"/>");
        for(String[] a:new String[][]{{"document","document.main"},{"styles","styles"},{"numbering","numbering"},{"header1","header"},{"footer1","footer"},{"settings","settings"}})types.append("<Override PartName=\"/word/").append(a[0]).append(".xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.").append(a[1]).append("+xml\"/>");
        types.append("<Override PartName=\"/docProps/core.xml\" ContentType=\"application/vnd.openxmlformats-package.core-properties+xml\"/></Types>");part("[Content_Types].xml",types.toString());
        var factory=DocumentBuilderFactory.newInstance();factory.setNamespaceAware(true);factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
        for(var entry:parts.entrySet())if(entry.getKey().endsWith(".xml")||entry.getKey().endsWith(".rels"))factory.newDocumentBuilder().parse(new ByteArrayInputStream(entry.getValue()));
        // Cada paràgraf i cada línia de codi han de continuar literals a l'Open XML.
        var dom=factory.newDocumentBuilder().parse(new ByteArrayInputStream(parts.get("word/document.xml")));
        var paras=dom.getElementsByTagNameNS(W,"p");List<String> actual=new ArrayList<>();
        for(int i=0;i<paras.getLength();i++){
            var ts=((org.w3c.dom.Element)paras.item(i)).getElementsByTagNameNS(W,"t");StringBuilder s=new StringBuilder();for(int k=0;k<ts.getLength();k++)s.append(ts.item(k).getTextContent());if(ts.getLength()>0)actual.add(s.toString());
        }
        if(!actual.equals(expected))throw new IllegalStateException("El text exportat no coincideix amb el Markdown");
        try(var zip=new ZipOutputStream(Files.newOutputStream(docx))){for(var a:parts.entrySet()){zip.putNextEntry(new ZipEntry(a.getKey()));zip.write(a.getValue());zip.closeEntry();}}
        System.out.printf("DOCX: %d paràgrafs/línies comprovats, %d taules, %d blocs de codi, %d imatges, 10 enllaços d'índex.%n",expected.size(),tables,codes,imageId);
    }
    static Color color(String hex){return new Color(Integer.parseInt(hex,16));}
    static void text(Graphics2D g,String value,int x,int y,int size,boolean bold){g.setFont(new Font(bold?"Poppins SemiBold":bodyFont,Font.PLAIN,size));g.drawString(value,x,y);}
    static void box(Graphics2D g,int x,int y,int w,int h,String title,String...lines){
        g.setColor(color("F3F5F3"));g.fillRoundRect(x,y,w,h,14,14);g.setColor(color(GREEN));g.setStroke(new BasicStroke(2));g.drawRoundRect(x,y,w,h,14,14);g.fillRoundRect(x,y,w,40,14,14);g.fillRect(x,y+25,w,15);g.setColor(Color.WHITE);text(g,title,x+16,y+29,24,true);g.setColor(color(INK));for(int i=0;i<lines.length;i++)text(g,lines[i],x+16,y+69+i*30,24,false);
    }
    static void arrow(Graphics2D g,int x1,int y1,int x2,int y2){
        g.setColor(color(GREEN));g.setStroke(new BasicStroke(3));g.drawLine(x1,y1,x2,y2);double angle=Math.atan2(y2-y1,x2-x1);int size=12;Polygon tri=new Polygon();tri.addPoint(x2,y2);tri.addPoint((int)(x2-size*Math.cos(angle-.5)),(int)(y2-size*Math.sin(angle-.5)));tri.addPoint((int)(x2-size*Math.cos(angle+.5)),(int)(y2-size*Math.sin(angle+.5)));g.fill(tri);
    }
    static void diagram(Path path)throws Exception{
        BufferedImage image=new BufferedImage(2400,1560,BufferedImage.TYPE_INT_RGB);Graphics2D g=image.createGraphics();g.scale(2,2);g.setColor(Color.WHITE);g.fillRect(0,0,1200,780);g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(color(GREEN));text(g,"DE LES CLAUS SQL ALS CAMPS JAVA",25,38,25,true);g.setColor(color(INK));text(g,"Model de relacions de unitat2_2627_simplificat",25,69,20,false);
        box(g,25,110,230,135,"TEACHER","id · PK","full_name");box(g,435,110,290,163,"COURSE","id · PK","name","teacher_id · FK, opcional");
        arrow(g,435,239,255,179);g.setColor(color(INK));text(g,"FK a PK",300,166,24,true);
        box(g,435,355,290,130,"ENROLLMENT","course_id · FK","student_id · FK");box(g,915,355,260,130,"STUDENT","id · PK","full_name");
        arrow(g,580,355,580,273);arrow(g,725,454,915,424);g.setColor(color(INK));text(g,"FK a PK",596,325,24,true);text(g,"FK a PK",768,408,24,true);
        g.setColor(color("595959"));text(g,"PK: clau primària · FK: clau forana",25,527,24,false);
        box(g,25,558,560,172,"PROFESSOR I CURSOS","Propietari: Course.teacher","Invers: Teacher.courses","mappedBy = \"teacher\"");
        box(g,610,558,565,172,"CURSOS I ESTUDIANTS","Propietari: Course.students","Invers: Student.courses","mappedBy = \"students\"");
        g.setColor(color("595959"));text(g,"Cada fila d’ENROLLMENT desa un enllaç entre un curs i un estudiant.",25,765,24,false);g.dispose();Files.createDirectories(path.getParent());ImageIO.write(image,"png",path.toFile());
    }
    static int process(List<String> args)throws Exception{ProcessBuilder pb=new ProcessBuilder(args).inheritIO();pb.environment().put("SAL_USE_VCLPLUGIN","svp");pb.environment().put("GSETTINGS_BACKEND","memory");return pb.start().waitFor();}
    public static void main(String[] args)throws Exception{
        repo=Path.of("").toAbsolutePath();while(!Files.exists(repo.resolve("skills/documents-alumnat-pau-casesnoves/SKILL.md"))){repo=repo.getParent();if(repo==null)throw new IllegalArgumentException("Executau des del repositori");}
        input=repo.resolve("materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.md");String lo="libreoffice";boolean overwrite=false;
        for(int i=0;i<args.length;i++)switch(args[i]){case "--entrada"->input=Path.of(args[++i]).toAbsolutePath();case "--sortida"->output=Path.of(args[++i]).toAbsolutePath();case "--libreoffice"->lo=args[++i];case "--sobreescriu"->overwrite=true;default->throw new IllegalArgumentException(args[i]);}
        if(output==null)output=input.getParent();Files.createDirectories(output);String base=input.getFileName().toString().replaceFirst("\\.md$","");Path docx=output.resolve(base+".docx"),pdf=output.resolve(base+".pdf");
        if(!overwrite && (Files.exists(docx)||Files.exists(pdf)))throw new IllegalArgumentException("Ja hi ha exportacions. Revisau-les abans de --sobreescriu.");
        Set<String> fonts=new HashSet<>(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        for(String name:List.of(bodyFont,"Poppins SemiBold","DejaVu Sans Mono"))if(!fonts.contains(name))throw new IllegalStateException("Falta la font "+name);
        String md=Files.readString(input);if(!md.contains("status: \"Revisat\""))throw new IllegalArgumentException("Cal Markdown revisat");md=md.replaceFirst("(?s)\\A---\\R.*?\\R---\\R","");
        diagram(input.getParent().resolve("recursos/relacions-universitat.png"));
        addImage(repo.resolve("skills/documents-alumnat-pau-casesnoves/assets/logo-pau-casesnoves.png"),2041,"CIFP Pau Casesnoves");
        parse(Arrays.asList(md.split("\\R",-1)));packageDocx(docx);
        Path temp=Files.createTempDirectory("ut2-export-");String filter="pdf:writer_pdf_Export:{\"UseTaggedPDF\":{\"type\":\"boolean\",\"value\":\"true\"},\"ExportBookmarks\":{\"type\":\"boolean\",\"value\":\"true\"},\"ExportBookmarksToPDFDestination\":{\"type\":\"boolean\",\"value\":\"true\"}}";
        int status=process(List.of(lo,"-env:UserInstallation="+temp.resolve("perfil").toUri(),"--headless","--convert-to",filter,"--outdir",temp.toString(),docx.toString()));
        Path fresh=temp.resolve(base+".pdf");if(status!=0||!Files.exists(fresh)||Files.size(fresh)<1000)throw new IOException("Ha fallat l'exportació; DOCX disponible a "+docx);
        Files.move(fresh,pdf,StandardCopyOption.REPLACE_EXISTING);System.out.println("DOCX: "+docx);System.out.println("PDF: "+pdf);
        try(var stream=Files.walk(temp)){for(Path path:stream.sorted(Comparator.reverseOrder()).toList())Files.deleteIfExists(path);}
    }
}
