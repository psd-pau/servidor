import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.regex.*;
import java.util.zip.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import org.w3c.dom.*;

/** Generador de la proposta concreta, sobre una còpia de la plantilla del centre.
 * Ús: java GeneraExamenReprografia.java plantilla.docx entrada.md sortida.docx [--solucionari]
 * No sobreescriu. Exportació PDF posterior amb LibreOffice i perfil temporal.
 */
public class GeneraExamenReprografia {
    static final String W = "http://schemas.openxmlformats.org/wordprocessingml/2006/main";
    static final String DC = "http://purl.org/dc/elements/1.1/";
    static Document doc;
    static Element body, section, normal, heading;
    static boolean newPage;
    static boolean solutionMode;

    static Document xml(byte[] data) throws Exception {
        var f = DocumentBuilderFactory.newInstance();
        f.setNamespaceAware(true);
        f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        return f.newDocumentBuilder().parse(new ByteArrayInputStream(data));
    }
    static byte[] bytes(Document d) throws Exception {
        var f = TransformerFactory.newInstance();
        f.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        f.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        var t = f.newTransformer();
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        var out = new ByteArrayOutputStream();
        t.transform(new DOMSource(d), new StreamResult(out));
        return out.toByteArray();
    }
    static Element child(Element e, String name) {
        for (Node n=e.getFirstChild(); n!=null; n=n.getNextSibling())
            if (n instanceof Element el && W.equals(el.getNamespaceURI()) && name.equals(el.getLocalName())) return el;
        Element result=e.getOwnerDocument().createElementNS(W, "w:"+name);
        e.appendChild(result); return result;
    }
    static void setting(Element parent, String name, String... attrs) {
        Element e=child(parent,name);
        for(int i=0;i<attrs.length;i+=2) e.setAttributeNS(W,"w:"+attrs[i],attrs[i+1]);
    }
    static void typography(Element rp, String font, int size) {
        setting(rp,"rFonts","ascii",font,"hAnsi",font,"eastAsia",font,"cs",font);
        setting(rp,"sz","val",Integer.toString(size));
        setting(rp,"szCs","val",Integer.toString(size));
        setting(rp,"lang","val","ca-ES","eastAsia","ca-ES","bidi","ca-ES");
    }
    static void run(Element p, String text, boolean bold, boolean code, int size) {
        Element r=doc.createElementNS(W,"w:r");
        Element templateRun=(Element)normal.getElementsByTagNameNS(W,"r").item(0);
        if(templateRun!=null) r.appendChild(child(templateRun,"rPr").cloneNode(true));
        Element rp=child(r,"rPr");
        typography(rp,code ? "DejaVu Sans Mono" : "Poppins",code ? 18 : size);
        setting(rp,"b","val",bold ? "true" : "false");
        Element t=doc.createElementNS(W,"w:t");
        t.setAttributeNS(XMLConstants.XML_NS_URI,"xml:space","preserve");
        t.setTextContent(text); r.appendChild(t); p.appendChild(r);
    }
    static Element paragraph(boolean title, boolean code, boolean blank) {
        Element p=(Element)(title ? heading : normal).cloneNode(true);
        for(Node n=p.getFirstChild(); n!=null;) {
            Node next=n.getNextSibling();
            if(!(n instanceof Element el && "pPr".equals(el.getLocalName()))) p.removeChild(n);
            n=next;
        }
        Element pp=child(p,"pPr");
        setting(pp,"spacing","before",title ? "160" : "0","after",title ? "120" : blank || code ? "0" : solutionMode ? "60" : "100", "line", blank ? "260" : code ? "220" : "240","lineRule",blank || code ? "exact" : "auto");
        setting(pp,"jc","val","left");
        setting(pp,"keepNext","val",title ? "true" : "false");
        setting(pp,"keepLines","val","true");
        setting(pp,"pageBreakBefore","val",newPage ? "true" : "false");
        typography(child(pp,"rPr"),code ? "DejaVu Sans Mono" : "Poppins",code ? 18 : title ? 24 : 22);
        if(code) setting(pp,"shd","val","clear","fill","F3F5F3");
        newPage=false; body.insertBefore(p,section); return p;
    }
    static void text(String line, boolean title) {
        Element p=paragraph(title,false,false);
        Matcher m=Pattern.compile("(\\*\\*[^*]+\\*\\*|`[^`]+`)").matcher(line);
        int pos=0;
        while(m.find()) {
            if(m.start()>pos) run(p,line.substring(pos,m.start()),title,false,title ? 24 : 22);
            String part=m.group(); boolean code=part.startsWith("`");
            run(p,part.substring(code ? 1 : 2,part.length()-(code ? 1 : 2)),title || !code,code,title ? 24 : 22);
            pos=m.end();
        }
        if(pos<line.length()) run(p,line.substring(pos),title,false,title ? 24 : 22);
    }
    static void metadata(Document d, String name, String value) {
        NodeList nodes=d.getElementsByTagNameNS(DC,name);
        Element el;
        if(nodes.getLength()==0) {el=d.createElementNS(DC,"dc:"+name); d.getDocumentElement().appendChild(el);}
        else el=(Element)nodes.item(0);
        el.setTextContent(value);
    }
    static void language(Document d) {
        NodeList rps=d.getElementsByTagNameNS(W,"rPr");
        for(int i=0;i<rps.getLength();i++) setting((Element)rps.item(i),"lang","val","ca-ES","eastAsia","ca-ES","bidi","ca-ES");
        NodeList langs=d.getElementsByTagNameNS(W,"lang");
        for(int i=0;i<langs.getLength();i++) ((Element)langs.item(i)).setAttributeNS(W,"w:val","ca-ES");
    }
    public static void main(String[] args) throws Exception {
        if(args.length<3 || args.length>4 || (args.length==4 && !args[3].equals("--solucionari")))
            throw new IllegalArgumentException("plantilla.docx entrada.md sortida.docx [--solucionari]");
        boolean solucionari=args.length==4;
        solutionMode=solucionari;
        Path output=Path.of(args[2]);
        if(Files.exists(output)) throw new IOException("La sortida ja existeix; revisa edicions manuals abans de regenerar.");
        Map<String,byte[]> parts=new LinkedHashMap<>();
        try(var zip=new ZipInputStream(Files.newInputStream(Path.of(args[0])))) {
            for(ZipEntry e;(e=zip.getNextEntry())!=null;) parts.put(e.getName(),zip.readAllBytes());
        }
        doc=xml(parts.get("word/document.xml"));
        body=(Element)doc.getElementsByTagNameNS(W,"body").item(0);
        normal=(Element)body.getElementsByTagNameNS(W,"p").item(0);
        section=(Element)doc.getElementsByTagNameNS(W,"sectPr").item(0);
        Element table=null;
        for(Node n=body.getFirstChild();n!=null;n=n.getNextSibling()) if(n instanceof Element e && "tbl".equals(e.getLocalName())) table=e;
        if(table==null) throw new IOException("Taula d'identificació absent");
        for(Node n=table.getNextSibling();n!=null && n!=section;) {
            Node next=n.getNextSibling(); if(n instanceof Element e && "p".equals(e.getLocalName())) heading=(Element)e.cloneNode(true);
            body.removeChild(n); n=next;
        }
        if(heading==null) throw new IOException("Paràgraf de cos absent");
        NodeList ts=doc.getElementsByTagNameNS(W,"t");
        for(int i=0;i<ts.getLength();i++) if("X".equals(ts.item(i).getTextContent())) ts.item(i).setTextContent("1");
        ZonedDateTime now=ZonedDateTime.now(ZoneId.of("Europe/Madrid"));
        int year=now.getYear()-(now.getMonthValue()<9 ? 1 : 0);
        String course=String.format("%02d/%02d",year%100,(year+1)%100);
        Map<String,String> values=Map.of("Curs",course,"Grup","IFC33C","Mòdul","Desenvolupament web en entorn servidor");
        NodeList cells=table.getElementsByTagNameNS(W,"tc");
        for(int i=0;i<cells.getLength();i++) {
            Element cell=(Element)cells.item(i); String value=values.get(cell.getTextContent());
            if(value==null) continue;
            Node n=cell.getNextSibling(); while(n!=null && !(n instanceof Element)) n=n.getNextSibling();
            Element target=(Element)n;
            if(target==null || !target.getTextContent().isBlank()) throw new IOException("Cel·la inesperada");
            Element p=child(target,"p"); run(p,value,false,false,22);
        }
        List<String> lines=Files.readAllLines(Path.of(args[1]),StandardCharsets.UTF_8);
        boolean front=false, code=false;
        List<Element> codeParagraphs=new ArrayList<>();
        int questions=0;
        for(int i=0;i<lines.size();i++) {
            String line=lines.get(i);
            if(i==0 && line.equals("---")) {front=true;continue;}
            if(front) {if(line.equals("---"))front=false;continue;}
            if(line.startsWith("```")) {
                if(!code && solucionari && section.getPreviousSibling() instanceof Element previous
                        && "p".equals(previous.getLocalName()))
                    setting(child(previous,"pPr"),"keepNext","val","true");
                if(code && solucionari && codeParagraphs.size()<=36) {
                    for(int j=0;j<codeParagraphs.size()-1;j++)
                        setting(child(codeParagraphs.get(j),"pPr"),"keepNext","val","true");
                }
                code=!code;codeParagraphs.clear();continue;
            }
            if(code) {
                Element p=paragraph(false,true,false);run(p,line,false,true,18);codeParagraphs.add(p);continue;
            }
            if(line.isBlank())continue;
            if(line.equals("<!-- pagina -->")) {newPage=true;continue;}
            Matcher space=Pattern.compile("<!-- resposta: (\\d+) -->").matcher(line);
            if(space.matches()) {
                for(int k=0;k<Integer.parseInt(space.group(1));k++)run(paragraph(false,false,true),"",false,false,22);
                continue;
            }
            if(line.startsWith("### ")) text(line.substring(4),true);
            else if(line.startsWith("## ")) {questions++;text(line.substring(3),true);}
            else if(line.startsWith("# "))text(line.substring(2),true);
            else text(line,false);
        }
        if(code || front || questions!=7) throw new IOException("Font incompleta o nombre d'apartats incorrecte");
        language(doc); parts.put("word/document.xml",bytes(doc));
        Document core=xml(parts.get("docProps/core.xml"));
        metadata(core,"creator","David Pons"); metadata(core,"language","ca-ES");
        metadata(core,"title",solucionari ? "UT1 — Reprografia — Solucionari" : "UT1 — Reprografia — Primera proposta");
        parts.put("docProps/core.xml",bytes(core));
        for(String name:new ArrayList<>(parts.keySet())) {
            if(!name.startsWith("word/") || !name.endsWith(".xml") || name.equals("word/document.xml"))continue;
            Document part=xml(parts.get(name)); language(part);
            if(name.startsWith("word/footer")) {
                NodeList texts=part.getElementsByTagNameNS(W,"t");
                for(int i=0;i<texts.getLength();i++) if(texts.item(i).getTextContent().equals("Criteris de qualificació:")) {
                    Element p=(Element)texts.item(i).getParentNode().getParentNode();
                    Element r=part.createElementNS(W,"w:r");
                    typography(child(r,"rPr"),"Poppins",16);
                    Element t=part.createElementNS(W,"w:t");
                    t.setTextContent(solucionari ? " Solucionari: punts indicats a cada apartat. Total: 10." : " Proposta: punts indicats a cada apartat. Total: 10.");r.appendChild(t);p.appendChild(r);
                }
            }
            if(name.equals("word/settings.xml")) setting(part.getDocumentElement(),"updateFields","val","true");
            parts.put(name,bytes(part));
        }
        Files.createDirectories(output.toAbsolutePath().getParent());
        try(var zip=new ZipOutputStream(Files.newOutputStream(output,StandardOpenOption.CREATE_NEW))) {
            for(var entry:parts.entrySet()) {zip.putNextEntry(new ZipEntry(entry.getKey()));zip.write(entry.getValue());zip.closeEntry();}
        }
        System.out.println("DOCX creat: "+output+"; curs "+course+"; 7 apartats; autoria David Pons; ca-ES.");
    }
}
