package top.nzhz.wiki.req;

public class MusicQueryReq extends PageReq{
    private Long id;

    private Long categoryId2;

    private String name;

    private String author;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Long categoryId2) {
        this.categoryId2 = categoryId2;
    }

    @Override
    public String toString() {
        return "MusicQueryReq{" +
                "id=" + id +
                ", categoryId2=" + categoryId2 +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                "} " + super.toString();
    }

}