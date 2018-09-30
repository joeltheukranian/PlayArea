//import groovy.util.*;

assert 5 == 5

def text = '''
			    <list>
			        <technology>
			            <name>Groovy2</name>
			        </technology>
			    </list>
			'''

def list = new XmlSlurper().parseText(text)
assert list instanceof groovy.util.slurpersupport.GPathResult
//assert list.technology.name == 'Groovy'

println list.technology.name;

