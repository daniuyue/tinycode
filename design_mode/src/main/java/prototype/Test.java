package prototype;

import java.util.Date;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        Date date = new Date();
        Video video = new Video("luoyue", date);



        Video video2 = (Video) video.clone();

        System.out.println(video);
        System.out.println("video.hashCode()->" + video.hashCode());

        System.out.println(video2);
        System.out.println("video2.hashCode()->" + video2.hashCode());

        date.setTime(123125123L);
        System.out.println(video);
        System.out.println("video.hashCode()->" + video.hashCode());

        System.out.println(video2);
        System.out.println("video2.hashCode()->" + video2.hashCode());

//        Video asd = new Video("asd", date);
//        System.out.println(asd.hashCode());
    }

}
