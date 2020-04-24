package deserialization;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XmlParser<T> {

    private JAXBContext context;
    private Unmarshaller unmarshaller;
    private Marshaller marshaller;

    /**
     * JAXBContext.newIstance() requires .class as an argument
     * wont work with generic T, need to inject it via constructor
     *
     */

    public XmlParser(Class classPath){

        try{
            context = JAXBContext.newInstance(classPath);
            unmarshaller = context.createUnmarshaller();
            marshaller = context.createMarshaller();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public T parse(File file) throws JAXBException {

        return (T) unmarshaller.unmarshal(file);

    }

    public T parse(InputStream inputStream) throws JAXBException{

        T t = null;
        try{
            t = (T) unmarshaller.unmarshal(inputStream);
        }catch (JAXBException e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;

    }

    public void marshall(T t, File file){
        try {
            marshaller.marshal(t,file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


}
