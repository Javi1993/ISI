package com.isi.master.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Example {
	public static void main(String[] args)  {
		MongoClient client = new MongoClient("localhost", 17017);//conectamos
		MongoDatabase database = client.getDatabase("test");//elegimos bbdd
		MongoCollection<Document> collection = database.getCollection("pruebas");//tomamos la coleccion
		collection.drop();//vaciamos la coleccion

		List<Document> textos = new ArrayList<Document>();
		for(int i = 0; i<1000; i++)
		{
			textos.add(new Document().append("texto", UUID.randomUUID().toString()));
		}

		collection.insertMany(textos);//insertamos una lista de documentos
		client.close();
	}
}
