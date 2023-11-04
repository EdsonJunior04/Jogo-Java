package pro.mongocrud;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

public class conectaMongo {
    public void getValues() {
    System.out.println("Método getValues()");
    MongoClient mongo = new MongoClient("localhost", 27017);
    MongoDatabase db = mongo.getDatabase("snake");
    MongoCollection<Document> docs = db.getCollection("snake");
    for (Document doc : docs.find()) {
        System.out.println("item: " + doc);
    }
    System.out.println("getValues() - ok - finalizou");
   }     
    
   public void insertValues(String nome, int score) {
        System.out.println("Método insertValues()");
        //conexão mongo
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase db = mongo.getDatabase("snake");
        MongoCollection<Document> docs = db.getCollection("snake");
        Entrada user = createUser(nome, score);
//cria um user obj da classe conectar, 
//chamando o método createUser() - logo abaixo
        Document doc = createDocument(user);
//cria um doc que referencia o conteúdo de user do createDocument()
//obs, o Banco só entende objetos do tipo Document, 
        docs.insertOne(doc);//insere no mongo o conteúdo de doc
        getValues();
        System.out.println("insertValues ok");
    }

   public Entrada createUser(String nome,int score) {
        //esse método deve ser uma entrada 
        //externa (interface, scanner ou JOptionPanel
        Entrada u = new Entrada();
        u.setNome(nome);
        u.setScore(score);
        return u;
    }

   public Document createDocument(Entrada user) {
        Document docBuilder = new Document();
        docBuilder.append("nome", user.getNome());
        docBuilder.append("score", user.getScore());
        return docBuilder;
    }

   

    public void updateValues() {

        System.out.println("updateValues");
        //Entrada user = createUser();
        MongoClient mongo = new MongoClient("localhost", 27017);

        MongoDatabase db = mongo.getDatabase("snake");
        MongoCollection<Document> docs = db.getCollection("snake");

        docs.updateOne(Filters.eq("nome", "Crishna"), Updates.set("cidadenasc", "Santa Maria - RS"));
        System.out.println("Documento teve sucesso no update...");
        for (Document doc : docs.find()) {
            System.out.println("item update: " + doc);
        }

    }

    public  void deleteValues() {
        System.out.println("deleteValues");
        //Entrada user = createUser();
        MongoClient mongo = new MongoClient("localhost", 27017);

        MongoDatabase db = mongo.getDatabase("snake");
        MongoCollection<Document> docs = db.getCollection("snake");

        docs.deleteOne(Filters.eq("nome", "Maria"));
        System.out.println("Documento teve sucesso no delete...");
        for (Document doc : docs.find()) {
            System.out.println("item update: " + doc);
        }

    }
}
