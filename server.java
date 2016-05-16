import java.util.*;
import java.net.ServerSocket;
import java.net.*;
import java.io.*;

class InterBunny {
	static String WEB_URL = //"http://localhost/InterBunny/";
							"http://interbunny.net23.net/";
							//"http://interbunny.byethost5.com/";
	public static void main(String[] args)  throws Exception  {
		Scanner in = new Scanner(System.in);
		System.out.print("Do you want to host your network?(Y/N) : ");
		boolean isServer = in.next().trim().equalsIgnoreCase("Y");
		if(!isServer) {
				ServerSocket Ssocket;
		Ssocket = new ServerSocket(7786);
			while(true){
					Socket s = Ssocket.accept();
					Thread t = new Thread(new InterBunnyConnectorClient(s));
					t.start();
				}
		
		}
		while(true){
		fetchAllReq();
	Thread.sleep(1000);
        	}
        }
		
		static String base64(String in ,boolean decode)  {
			try{
			Runtime runtime = Runtime.getRuntime();
            Process p;
            if(decode)
            	p = runtime.exec("base64 -d -w 0"  );
			else
				p = runtime.exec("base64 -w 0"  );

            BufferedReader stdInput = new BufferedReader(new
                     InputStreamReader(p.getInputStream()));

            OutputStream out = p.getOutputStream();
            out.write(in.getBytes());
             out.close();

             StringBuilder content=new StringBuilder();
             String line = null;
             while((line=stdInput.readLine())!=null)
             {
             	content.append(line);
             	content.append("\n");

             }
			stdInput.close();  

			//System.out.println("((((((\n"+content.toString()+"\n))))))))))");
			return content.toString();
			}catch(IOException e) {
				System.err.println(e);
			}         
			return "ERROR!";
		}



	
static String  makeSimple(String s) {
	for(int i=0;i<5;i++){
int h=s.lastIndexOf(System.getProperty("line.separator"));
if(h<0) break;
	s = s.substring(0,h);
}
return s.trim();
}

	static void fetchAllReq() throws Exception {

       URL url = new URL(InterBunny.WEB_URL+"index.php");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("myname", "server");
        params.put("files", "1");
       
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        String response = sb.toString();

        try{
        if(response!=null && !response.isEmpty())
        	{

        	//	System.out.println("ALLLLLLL LIST\n=============\n"+response+"\n===========");
        		response = InterBunny.makeSimple(response);
        	//	System.out.println("ALLLLLLL LIST\n=============\n"+response+"\n===========");
        		BufferedReader bufReader = new BufferedReader(new StringReader(response));
				String line=null;
				
				while( (line=bufReader.readLine()) != null )
				if(!line.isEmpty())
				{
					System.out.println("Conecting : "+(Long.parseLong(line.trim())));
					InterBunnyConnector ibc = new InterBunnyConnector(Long.parseLong(line.trim()));
					ibc.connect("");
				}
			}
		}catch(Exception e2) {
			System.err.println(">>>>>>>>>>>>>>>>>>>>>>>\n"+e2+"\n   >>>>>>>>>>>>>>\n"+response);
		}
   }

	
}



class InterBunnyConnector {
	long id;
	InterBunnyConnector(long i){
		id = i;
	}
    void connect(String data) throws Exception {
        URL url = new URL(InterBunny.WEB_URL+"index.php");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("myname", "server");
        params.put("oname", "client");
        params.put("id", ""+id);
        
        if(data!=null && !data.isEmpty())
        	params.put("data", InterBunny.base64(data,false));
        
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        String response = sb.toString();
//	System.out.println("TYP2 : \n----------------\n"+response+"\n--------------------");
        	
        if(response!=null && !response.isEmpty())
        	{
        		response = InterBunny.makeSimple(response);
        		fetch(InterBunny.base64(response,true));
        	}

    }

    void fetch(String response) {
    	System.out.println("SOCKET FETCHING\n+++++++++++++\n"+response+"\n+++++++++");
    	int ind = response.indexOf('\n');
    	if(ind<0)
    		ind = response.length();

    	String res = response.substring(0,ind);


    	String fl[] = res.split(" ");
    	if(fl.length==3) {
    		String host = fl[1];
    		////////////////////////
    		String t = fl[1].substring(8); 
    		int ind2 = t.indexOf('/');
    		res = fl[0]+" "+t.substring(ind2)+" "+fl[2];
    		response = res+response.substring(ind);
        try{
    		host = InetAddress.getByName(new URL(host).getHost()).getHostAddress();
		
    		Socket socket = new Socket(host,80);
    		socket.setSoTimeout(10000);
				 OutputStreamWriter osw;
     		 osw = new OutputStreamWriter(socket.getOutputStream());
     			osw.write(response+"\r\n\r\n\r\n\r\n");
     			System.out.println("TRYING\n=============\n"+response);
    	
     			osw.flush();
             InputStreamReader isr;
 		 	 isr = new InputStreamReader(socket.getInputStream());
     		StringBuilder in = new StringBuilder();
				try{while(true) {
					int x = isr.read();
					if(x>=0)
					in.append((char)x);
				else break;
				}
			}catch(SocketTimeoutException ee) {

			}
				isr.close();
				socket.close();

//				System.out.println("\nResponse\n======= "+in.toString());
				connect(in.toString());

			}catch(Exception e){
				System.err.println(e.toString());
			}

    	}
        
    }


}


class InterBunnyConnectorClient implements Runnable {
	 Socket socket;
	 OutputStreamWriter osw;
        InputStreamReader isr;
        Random r = new Random();
        long id = r.nextInt((int)1e9);
 		

   	InterBunnyConnectorClient(Socket ssss) {
	socket = ssss;
		
    }
    public void run() {
    	try {
		socket.setSoTimeout(200);
		 isr = new InputStreamReader(socket.getInputStream());
       osw = new OutputStreamWriter(socket.getOutputStream());
      
       new Inner();
		while(true) {
				StringBuilder in = new StringBuilder();
				try{
				while(true) {
					int x = isr.read();
					if(x>=0)
					in.append((char)x);
				}
		     	}catch(Exception e){

		     	}
		     	String s = in.toString();
		     	if(!s.isEmpty()) {
//		     		System.out.println(">>"+s+"<<");
		     		connect(s);
		     		
		     	}

	   }
	}
	catch (Exception e) {
		System.out.println(e);
	}
    }
    void connect(String data) throws Exception {
        URL url = new URL(InterBunny.WEB_URL+"index.php");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("myname", "client");
        params.put("oname", "server");
        params.put("id", ""+id);
        
        
        if(data!=null && !data.isEmpty())
        	params.put("data", InterBunny.base64(data,false));
        
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        String response = sb.toString();

        if(response!=null && !response.isEmpty())
        	fetch(InterBunny.base64(InterBunny.makeSimple( response),true));
    }

    void fetch(String response) throws IOException {
    	System.out.println("RES : "+id+"\n>>"+response);
    	if(!response.isEmpty())
		    {
		    	osw.write(response);
		    	osw.flush();
		    	osw.close();
		    	socket.close();
		    }

        
    }
    class Inner implements Runnable {
    	Thread t;
    	Inner() {
    		t = new Thread(this);
    		t.start();
    	}
    	public void run() {
    		while(true) {
    			try {
    				t.sleep(500);
    				connect("");
    			}catch (Exception e) {
    				
    			}
    		}
    	}
    }


}