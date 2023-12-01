package Admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AdminService {

	 private static String theaternumMax = "2600"; 
	 private static String theaternumMin = "1"; 
	 
	 public void AddTheater(){
		String lastKey = null;
		String userdir = System.getProperty("user.dir") + "./src/";
	    String path = userdir +"theater.txt";
	    ArrayList<String> theaterNamelist = new ArrayList<String>();
	    ArrayList<String> theaterKeylist = new ArrayList<String>();

        String num = null;
         
         	try {
        	 try (BufferedReader mvbr = new BufferedReader
                    (new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {

             String line;
             while ((line = mvbr.readLine()) != null) {
                 line = line.trim();
                 String[] info = line.split(" ");
                 theaterKeylist.add(info[0]);
                 theaterNamelist.add(info[1]);
             }
             
             String[] info2 = theaterKeylist.get(theaterKeylist.size()-1).split("");
             String[] info3 = new String[info2.length-1];
             for(int j=1; j<info2.length; j++) {
                  info3[j-1] = info2[j];
             }
             lastKey = String.join("", info3);
        	 }
        	 
             System.out.println("추가할 상영관 이름을 입력하십시오.\n");
               
             @SuppressWarnings("resource")
     		 Scanner scan = new Scanner(System.in);
             boolean flag1 = false; // 관 번호 체크
             lp1:
             while (!flag1) {
            	System.out.print("MovieReservation >> ");
                String input = scan.nextLine();
                if (!Pattern.matches("^[0-99]+$", input)) {
                   System.out.println("..! 오류 : 잘못된 입력입니다. 다시 입력해주세요.\n");
                   continue lp1;
                }
                
                for(String st : theaterNamelist) {
                   if(st.equals(input)) {
                      System.out.println("..! 오류 : 같은 상영관 번호가 있습니다. 다시 입력해주세요.\n");
                       continue lp1;
                   }
                }
                
                System.out.println("추가할 상영관의 좌석 수를 입력하십시오.\n");
                boolean flag2 = false;
                lp2:
                while(!flag2) {
		             flag1 = true;
		 	          
		 	         System.out.print("MovieReservation >> ");
		 	         scan = new Scanner(System.in);
		 	         num = scan.nextLine();
		 	          
		 	         if (!Pattern.matches("^[0-9]*$", num)) {
		                 System.out.println("..! 오류 : 잘못된 입력입니다. 다시 입력해주세요.\n");
		                 continue lp2;
		             }
		 	         
		 	         if((Integer.parseInt(num) < Integer.parseInt(theaternumMin)) || (Integer.parseInt(num) > Integer.parseInt(theaternumMax))) {
		                System.out.println("..! 오류 : 가능한 좌석 수가 아닙니다. 다시 입력해주세요.\n");
		                continue lp2;
		             }
		 	        
		             flag2 = true;
                }
		          
	            File file = new File(path);
	            if (!file.exists()) {
	                System.out.println("Cannot find file");
	                return;
	            }
	            FileWriter fw = new FileWriter(file, true);
	            BufferedWriter writer = new BufferedWriter(fw);
	            String str = "T" + Integer.toString(Integer.parseInt(lastKey) + 1) + " " + input + " " + num;
	            writer.write(str + System.lineSeparator());
	            writer.close();
             }
            
         } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); 
         } catch(IOException e) {
            e.printStackTrace();
         }


    }
	    
	public void AddMovieInfo(){
		        
	}
		
	public void AddMovie(){
		        
	}
	
    public void EditTheaterNum() {
        /* 관번호 수정 */
        try {
            // 1.
            // 관리자에게 수정할 관 번호 선택
            System.out.println("수정할 관 번호가 담긴 리스트입니다. \n");
            String userdir = System.getProperty("user.dir") + "./src/user/";
            String srcdir = System.getProperty("user.dir") + "./src/";
            String path = srcdir + "theater.txt";
            BufferedReader membr = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            StringBuffer inputBuffer = new StringBuffer();
            ArrayList<String> movieList = new ArrayList<>();
            String line;

            while ((line = membr.readLine()) != null) {
                movieList.add(line);
            }

            membr.close();
            for (int i = 0; i < movieList.size(); i++) {
                System.out.println((i + 1) + ". " + movieList.get(i));
            }
            System.out.println();

            System.out.println("수정할 리스트의 번호를 입력하십시오.");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            // 오류 처리 해줘야함
            int result = Integer.parseInt(input.replaceAll("\\s+", ""));

            System.out.println("수정할 관 번호를 입력하십시오.");
            input = scan.nextLine();
            // 오류 처리 해줘야함
            String repString = input.replaceAll("\\s+", "");
            String[] info = movieList.get(result - 1).split(" ");
            String theaterKey = info[0];

            // theater.txt에 수정된 내역 적용
            path = srcdir + "theater.txt";
            membr = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

            while ((line = membr.readLine()) != null) {
                info = line.split(" ");
                if (info[0].equals(theaterKey))
                    info[1] = repString;
                line = "";
                for (String str : info) {
                    line += str + " ";
                }
                inputBuffer.append(line);
                inputBuffer.append("\n");
            }

            membr.close();

            FileOutputStream fileout = new FileOutputStream(path);
            fileout.write(inputBuffer.toString().getBytes());
            fileout.close();
            scan.close();

            /* movie.txt (상영 스케줄)에 수정된 내역 적용 */
            path = srcdir + "movie.txt";
            membr = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String movieKey = "";

            while ((line = membr.readLine()) != null) {
                info = line.split(" ");
                if (info[1].equals(theaterKey))
                    movieKey = info[0];
                info[3] = repString;
                line = "";
                for (String str : info) {
                    line += str + " ";
                }
                inputBuffer.append(line);
                inputBuffer.append("\n");
            }

            membr.close();

            fileout = new FileOutputStream(path);
            fileout.write(inputBuffer.toString().getBytes());
            fileout.close();
            scan.close();

            // 예매 내역 반영

<<<<<<< HEAD
            path = userdir + "movie.txt"; // 바꿔야함
            membr = new BufferedReader(
=======
            File folder = new File(userdir);
            File[] filelist = folder.listFiles();
            if (filelist != null) {
                for (File file : filelist) {
                    if (file.isFile() && file.canRead()) {
                        FileReader filereader = new FileReader(file);
                        BufferedReader bufReader = new BufferedReader(filereader);
                        while ((line = bufReader.readLine()) != null) {
                            info = line.split(" ");
                            if (info[0].equals(movieKey))
                                info[3] = repString;
                            line = "";
                            for (String str : info) {
                                line += str + " ";
                            }
                            inputBuffer.append(line);
                            inputBuffer.append("\n");
                        }
                        bufReader.close();

                        fileout = new FileOutputStream(path);
                        fileout.write(inputBuffer.toString().getBytes());
                        fileout.close();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void EditTheaterSeat() {
        /* 전체 좌석 수 변경 -> 예매 내역 제외 후 반영하기 */
        String userdir = System.getProperty("user.dir") + "./src/user/";
        String srcdir = System.getProperty("user.dir") + "./src/";
        try {

            /* 1. 제외시킬 내역을 배열에 담기 */

            File folder = new File(userdir);
            File[] filelist = folder.listFiles();
            ArrayList<String> tkeyinIDList = new ArrayList<>();
            String[] info;
            String line;
            // id.txt에서 예매가 된 내역의 Theater 키값 추출
            if (filelist != null) {
                for (File file : filelist) {
                    if (file.isFile() && file.canRead()) {
                        FileReader filereader = new FileReader(file);
                        BufferedReader bufReader = new BufferedReader(filereader);

                        // id.txt 속의 예매된 theater 키값 추출
                        info = null;
                        while ((line = bufReader.readLine()) != null) {
                            info = line.split(" ");
                            if (info[0].matches("^[M-Z][\\d]+$")) {
                                tkeyinIDList.add(info[1]);
                            }
                        }
                        bufReader.close();
                    }
                }
            }

            // 중복제거
            for (String item : tkeyinIDList) {
                if (!tkeyinIDList.contains(item))
                    tkeyinIDList.add(item);
            }

            /* 2. theater.txt에 저장되어있는 키값 추출 */
            String path = srcdir + "theater.txt";
            BufferedReader bufReader = new BufferedReader(
>>>>>>> branch 'main' of https://github.com/zziglet/Moviereservation.git
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

            ArrayList<String> theaterList = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                theaterList.add(line);
            }
            bufReader.close();

            /* 3. 각 값을 비교해 같으면 제외(삭제) */
            info = null;
            for (String str : theaterList) {
                info = str.split(" ");
                if (tkeyinIDList.contains(info[0])) {
                    theaterList.remove(str);
                }
            }

            /* 4. 제외된 내역 출력 */
            System.out.println("수정할 좌석 수가 담긴 리스트입니다. \n");
            for (int i = 0; i < theaterList.size(); i++) {
                System.out.println((i + 1) + ": " + theaterList.get(i));
            }
            System.out.println("수정할 리스트의 번호를 입력하십시오.");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            // 오류 처리 해줘야함
            int result = Integer.parseInt(input.replaceAll("\\s+", ""));
            

            System.out.println("수정할 좌석 수를 입력하십시오.");
            input = scan.nextLine();
            // 오류 처리 해줘야함
            String seatsNum = input.replaceAll("\\s+", "");
            info = theaterList.get(result - 1).split(" ");
            String theaterKey = info[0];

            // theater.txt에 수정된 내역 적용
            
            path = srcdir + "theater.txt";
            bufReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            StringBuffer inputBuffer = new StringBuffer();

            while ((line = bufReader.readLine()) != null) {
                info = line.split(" ");
                if (info[0].equals(theaterKey))
                    info[2] = seatsNum;
                line = "";
                for (String str : info) {
                    line += str + " ";
                }
                inputBuffer.append(line);
                inputBuffer.append("\n");
            }

            bufReader.close();

            FileOutputStream fileout = new FileOutputStream(path);
            fileout.write(inputBuffer.toString().getBytes());
            fileout.close();
            scan.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public void EditTheaterSeat() {
        /* 전체 좌석 수 변경 -> 예매 내역 제외 후 반영하기 */
        String userdir = System.getProperty("user.dir") + "./src/user/";
        String srcdir = System.getProperty("user.dir") + "./src/";
        try {

            /* 1. 제외시킬 내역을 배열에 담기 */

            File folder = new File(userdir);
            File[] filelist = folder.listFiles();
            ArrayList<String> tkeyinIDList = new ArrayList<>();
            String[] info;
            String line;
            // id.txt에서 예매가 된 내역의 Theater 키값 추출
            if (filelist != null) {
                for (File file : filelist) {
                    if (file.isFile() && file.canRead()) {
                        FileReader filereader = new FileReader(file);
                        BufferedReader bufReader = new BufferedReader(filereader);

                        // id.txt 속의 예매된 theater 키값 추출
                        info = null;
                        while ((line = bufReader.readLine()) != null) {
                            info = line.split(" ");
                            if (info[0].matches("^[M-Z][\\d]+$")) {
                                tkeyinIDList.add(info[1]);
                            }
                        }
                        bufReader.close();
                    }
                }
            }
            // 중복제거
            for (String item : tkeyinIDList) {
                if (!tkeyinIDList.contains(item))
                    tkeyinIDList.add(item);
            }

            /* 2. theater.txt에 저장되어있는 키값 추출 */
            String path = srcdir + "theater.txt";
            BufferedReader bufReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            
            ArrayList<String> theaterList = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                theaterList.add(line);
            }
            bufReader.close();

            /* 3. 각 값을 비교해 같으면 제외(삭제) */
            info = null;
            for(String str : theaterList){
                info = str.split(" ");
                if(tkeyinIDList.contains(info[0])){
                    theaterList.remove(str);
                }
            }
            
            /* 4. 제외된 내역 출력 */
            System.out.println("수정할 좌석 수가 담긴 리스트입니다. \n");
            for(int i =0; i < theaterList.size();i++){
                System.out.println((i+1)+": "+theaterList.get(i));
            }
            System.out.println("수정할 리스트의 번호를 입력하십시오.");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            // 오류 처리 해줘야함
            int result = Integer.parseInt(input.replaceAll("\\s+", ""));

            System.out.println("수정할 좌석 수를 입력하십시오.");
            input = scan.nextLine();
            // 오류 처리 해줘야함
            String seatsNum = input.replaceAll("\\s+", "");
            info = theaterList.get(result-1).split(" ");
            String theaterKey = info[0];

            // theater.txt에 수정된 내역 적용

            path = srcdir + "theater.txt";
            bufReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            StringBuffer inputBuffer = new StringBuffer();
            
            while ((line = bufReader.readLine()) != null) {
                info = line.split(" ");
                if (info[0].equals(theaterKey))
                    info[2] = seatsNum;
                line = "";
                for (String str : info) {
                    line += str + " ";
                }
                inputBuffer.append(line);
                inputBuffer.append("\n");
            }

            bufReader.close();

            FileOutputStream fileout = new FileOutputStream(path);
            fileout.write(inputBuffer.toString().getBytes());
            fileout.close();
            scan.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

=======
>>>>>>> branch 'main' of https://github.com/zziglet/Moviereservation.git
    public void EditTheaterInfoName(String keyString, String moviename) {
        try {
            /* 영화 제목 수정 -> 예매 내역 반영, 상영 스케줄 반영 */
            // 1.
            // 관리자에게 수정할 제목 리스트 제공
            System.out.println("수정할 제목이 담긴 리스트입니다. \n");
            String userdir = System.getProperty("user.dir") + "./src/user/";
            String srcdir = System.getProperty("user.dir") + "./src/";
            String path = srcdir + "movieinfo.txt";
            BufferedReader membr = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            StringBuffer inputBuffer = new StringBuffer();
            ArrayList<String> movieList = new ArrayList<>();
            String line;

            while ((line = membr.readLine()) != null) {
                movieList.add(line);
            }

            membr.close();
            for (int i = 0; i < movieList.size(); i++) {
                System.out.println((i + 1) + ". " + movieList.get(i));
            }
            System.out.println();

            System.out.println("수정할 리스트의 번호를 입력하십시오.");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            // 오류 처리 해줘야함
            int result = Integer.parseInt(input.replaceAll("\\s+", ""));

            System.out.println("수정할 제목을 입력하십시오.");
            input = scan.nextLine();
            scan.close();
            // 오류 처리 해줘야함
            String repString = input.replaceAll("\\s+", "");
            String[] info = movieList.get(result - 1).split(" ");
            String movieinfoKey = info[0];

            /// movieinfo.txt 에 수정된 내역 적용
            path = srcdir + "movieinfo.txt";
            membr = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

            while ((line = membr.readLine()) != null) {
                info = line.split(" ");
                if (info[0].equals(movieinfoKey))
                    info[1] = repString;
                ;
                line = "";
                for (String str : info) {
                    line += str + " ";
                }
                inputBuffer.append(line);
                inputBuffer.append("\n");
            }

            membr.close();

            FileOutputStream fileout = new FileOutputStream(path);
            fileout.write(inputBuffer.toString().getBytes());
            fileout.close();

            //// movie.txt (상영 스케줄)에 수정된 내역 적용

            path = srcdir + "movie.txt";
            membr = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String movieKey = "";

            while ((line = membr.readLine()) != null) {
                info = line.split(" ");
                if (info[2].equals(movieinfoKey))
                    info[4] = repString;
                line = "";
                for (String str : info) {
                    line += str + " ";
                }
                inputBuffer.append(line);
                inputBuffer.append("\n");
            }

            membr.close();

            fileout = new FileOutputStream(path);
            fileout.write(inputBuffer.toString().getBytes());
            fileout.close();

           // 예매 내역 반영

            File folder = new File(userdir);
            File[] filelist = folder.listFiles();
            if (filelist != null) {
                for (File file : filelist) {
                    if (file.isFile() && file.canRead()) {
                        FileReader filereader = new FileReader(file);
                        BufferedReader bufReader = new BufferedReader(filereader);
                        while ((line = bufReader.readLine()) != null) {
                            info = line.split(" ");
                            if (info[0].equals(movieKey))
                                info[4] = repString;
                            line = "";
                            for (String str : info) {
                                line += str + " ";
                            }
                            inputBuffer.append(line);
                            inputBuffer.append("\n");
                        }
                        bufReader.close();

                        fileout = new FileOutputStream(path);
                        fileout.write(inputBuffer.toString().getBytes());
                        fileout.close();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void EditTheaterRuntime() {
        /* 러닝 타임 수정 -> 상영스케줄에 등록 내역 제외 후 상영스케줄 반영 */
        /* 1. 제외시킬 내역을 배열에 담기 */

        String srcdir = System.getProperty("user.dir") + "./src/";
        try {
            String path = srcdir + "movie.txt";
            BufferedReader bufReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            // 상영스케줄에 존재하는 영화 키값 추출
            String[] info = null;
            String line;
            ArrayList<String> infokeyinmovieList = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                info = line.split(" ");
                if (info[0].matches("^[M-Z][\\d]+$")) {
                    infokeyinmovieList.add(info[2]);
                }
            }
            bufReader.close();

            // 중복제거
            for (String item : infokeyinmovieList) {
                if (!infokeyinmovieList.contains(item))
                    infokeyinmovieList.add(item);
            }

            /* 2. movieinfo.txt에 저장되어있는 키값 추출 */
            path = srcdir + "movieinfo.txt";
            bufReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

            ArrayList<String> runningList = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                runningList.add(line);
            }
            bufReader.close();

            /* 3. 각 값을 비교해 같으면 제외(삭제) */
            info = null;
            for (String str : runningList) {
                info = str.split(" ");
                if (infokeyinmovieList.contains(info[0])) {
                    runningList.remove(str);
                }
            }
            /* 4. 제외된 내역 출력 */
            System.out.println("수정할 좌석 수가 담긴 리스트입니다. \n");
            for (int i = 0; i < runningList.size(); i++) {
                System.out.println((i + 1) + ": " + runningList.get(i));
            }
            System.out.println("수정할 리스트의 번호를 입력하십시오.");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            // 오류 처리 해줘야함
            int result = Integer.parseInt(input.replaceAll("\\s+", ""));

            System.out.println("수정할 러닝타임 기간을 입력하십시오.");
            input = scan.nextLine();
            // 오류 처리 해줘야함
            String runningTime = input.replaceAll("\\s+", "");
            info = runningList.get(result - 1).split(" ");
            String movieinfoKey = info[0];

            // movieinfo.txt에 수정된 내역 적용

            path = srcdir + "moiveinfo.txt";
            bufReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            StringBuffer inputBuffer = new StringBuffer();

            while ((line = bufReader.readLine()) != null) {
                info = line.split(" ");
                if (info[0].equals(movieinfoKey))
                    info[2] = runningTime;
                line = "";
                for (String str : info) {
                    line += str + " ";
                }
                inputBuffer.append(line);
                inputBuffer.append("\n");
            }

            bufReader.close();

            FileOutputStream fileout = new FileOutputStream(path);
            fileout.write(inputBuffer.toString().getBytes());
            fileout.close();
            scan.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}