package someGroovy

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser

def print_away() {
	def textWithXML = '''
			    <list>
			        <technology>
			            <name>Groovy From Package</name>
			        </technology>
			    </list>
			'''

	
	def list = new XmlSlurper().parseText(textWithXML)
	assert list instanceof groovy.util.slurpersupport.GPathResult
	//assert list.technology.name == 'Groovy'
	
	println list.technology.name;
	
	3.times {
		println "United United...";
	}
	
	myBlock = {
		println "United United...Block Block";
	}
	
	myBlock.call();
}


class Player {
	String name
	int age
}
class Club {
	String name
	String city
}

public printNameOfTheFootballingEntity(entity) {
	println "the entity with name "+ entity.name + " is related to Football"
}

public printPlayers() {
	player = new Player(name:"Giggs", age:35)
	club = new Club(name:"ManchesterUnited", city:"Old Trafford")
	printNameOfTheFootballingEntity(player)
	printNameOfTheFootballingEntity(club)
}

public parseJSON() {
	JsonParser parser = new JsonParser();
	JsonObject json = parser.parse("{ a: 'b'}").getAsJsonObject();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	println( "JSON: " + gson.toJson(json));
}




