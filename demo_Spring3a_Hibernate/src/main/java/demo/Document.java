package demo;


class Document {
    public String id, text;
	public String language;

    public Document(String id, String text){
        this.id = id;
        this.text = text;
    }
    
    public Document(String id, String language, String text){
        this.id = id;
        this.language = language;
        this.text = text;
    }
}