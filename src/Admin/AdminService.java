package Admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AdminService {
	 private static String theaternumMax = "120"; 
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
             System.out.print("MovieReservation >> ");
             
             @SuppressWarnings("resource")
     		 Scanner scan = new Scanner(System.in);
             String input = scan.nextLine();
             boolean flag1 = false; // 관 번호 체크
             while (!flag1) {
                if (!Pattern.matches("^[0-99]+$", input)) {
                   System.out.println("..! 오류 : 잘못된 입력입니다. 다시 입력해주세요.\n");
                   continue;
                }
                
                for(String st : theaterNamelist) {
                   if(st.equals(input)) {
                      System.out.println("..! 오류 : 같은 상영관 번호가 있습니다. 다시 입력해주세요.\n");
                       continue;
                   }
                }
                
                flag1 = true;
                boolean flag2 = false;
                while(!flag2) {
     	          System.out.println("추가할 상영관의 좌석 수를 입력하십시오.\n");
     	          System.out.print("MovieReservation >> ");
     	          scan = new Scanner(System.in);
     	          num = scan.nextLine();

	               if((Integer.parseInt(num) < Integer.parseInt(theaternumMin)) || (Integer.parseInt(num) > Integer.parseInt(theaternumMax))) {
	                 System.out.println("..! 오류 : 가능한 좌석 수가 아닙니다. 다시 입력해주세요.\n");
	                 continue;
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

            //////////////////////////////////////////////////////////
            //// movie.txt (상영 스케줄)에 수정된 내역 적용

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

            path = userdir + "movie.txt";
            membr = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            while ((line = membr.readLine()) != null) {
                info = line.split(" ");
                if (info[0].equals(movieKey))
                    info[1] = repString;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void EditTheaterSeat(String keyString, int seatNum) {

    }

    public void EditTheaterInfoName(String keyString, String moviename) {
        try {
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
            // 오류 처리 해줘야함
            String repString = input.replaceAll("\\s+", "");
            String[] info = movieList.get(result - 1).split(" ");
            String theaterKey = info[0];

            //// movie.txt (상영 스케줄)에 수정된 내역 적용

            path = srcdir + "movie.txt";
            membr = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String movieKey = "";

            while ((line = membr.readLine()) != null) {
                info = line.split(" ");
                if (info[1].equals(theaterKey))
                    movieKey = info[0];
                info[5] = repString;
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

            // 예매 내역 반영

            path = userdir + "movie.txt";
            membr = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            while ((line = membr.readLine()) != null) {
                info = line.split(" ");
                if (info[0].equals(movieKey))
                    info[2] = repString;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void EditTheaterRuntime(String keyString, int runningTime) {

    }
}
