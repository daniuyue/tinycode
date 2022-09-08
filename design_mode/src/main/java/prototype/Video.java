package prototype;

import lombok.Data;

import java.util.Date;

@Data
public class Video implements Cloneable {
    private String name;
    private Date createTime;

//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        Video v = (Video) super.clone();
//        v.createTime = (Date) this.createTime.clone();
//        return v;
//    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Video(String name, Date createTime) {
        this.name = name;
        this.createTime = createTime;
    }
}
