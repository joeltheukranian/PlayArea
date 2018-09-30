
def print_away() {
	def textWithXML = '''
			    <list>
			        <technology>
			            <name>Groovy2</name>
			        </technology>
			    </list>
			'''

	
	def list = new XmlSlurper().parseText(textWithXML)
	assert list instanceof groovy.util.slurpersupport.GPathResult
	//assert list.technology.name == 'Groovy'
	
	println list.technology.name;
}


