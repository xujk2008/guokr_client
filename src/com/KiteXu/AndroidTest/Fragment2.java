package com.KiteXu.AndroidTest;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment2 extends Fragment{

	private static final String NAMESPACE = "http://WebXml.com.cn/";
	 private static final String METHOD_NAME = "getWeatherbyCityName";
	 private static String URL =
	 "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";
	 private static String SOAP_ACTION =
	 "http://WebXml.com.cn/getWeatherbyCityName";

//	 private static final String NAMESPACE = "http://tempuri.org/";
//	 private static final String METHOD_NAME = "GetAudioCategoryList";
//	 private static String URL = "http://www.blc.org.cn/WebServices/TPIWebService.asmx?WSDL";
//	 private static String SOAP_ACTION = "http://tempuri.org/GetAudioCategoryList";

//	private static final String NAMESPACE = "http://tempuri.org/";
//	private static final String METHOD_NAME = "GetAudioDataListByParams";
//	private static String URL = "http://www.blc.org.cn/WebServices/TPIWebService.asmx?WSDL";
//	private static String SOAP_ACTION = "http://tempuri.org/GetAudioDataListByParams";
	private SoapObject detail;
	
	private View view = null;

	public static Fragment2 newInstance() {
		Fragment2 fragment = new Fragment2();
		return fragment;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		TextView fragment2Text = (TextView)(view.findViewById(R.id.fragment2Text));
		fragment2Text.setText("Fragment2");
		
		Button getButton = (Button)(view.findViewById(R.id.getButton));
		getButton.setText("get");
		getButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				new Thread() {
					public void run() {
						getData();
					}
				}.start();

			}
		});

	}

	public void getData() {

//		HttpPost post = new HttpPost(URL);
//		String request = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
//				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
//				+ "<soap:Body>"
//				+ "<GetAudioDataList xmlns=\"http://tempuri.org/\">"
//				+ "<SearchCondition>" + "<CategoryCode>YPZY09</CategoryCode>"
//				+ "<IsAllRecord>0</IsAllRecord>"
//				+ "<CurrentPageIndex>1</CurrentPageIndex>"
//				+ "<PageSize>10</PageSize>" + "</SearchCondition>"
//				+ "</GetAudioDataList>" + "</soap:Body>" + "</soap:Envelope>";
//		try {
////			HttpEntity entity = new StringEntity(request);
////			post.setEntity(entity);
////			post.setHeader("SOAPAction", SOAP_ACTION);
////			CloseableHttpClient httpClient = HttpClients.createDefault();
////			httpClient.execute(post);
//			HttpClient httpClient = new DefaultHttpClient();
//			post.addHeader("SOAPAction", SOAP_ACTION);
//			StringEntity entity = new StringEntity(request);
//			post.setEntity(entity);
//			HttpResponse response = httpClient.execute(post);
//			Log.v("@@@@@", response.getStatusLine().getStatusCode() + "");
//			Log.v("@@@@@", EntityUtils.toString(response.getEntity(), "UTF-8"));
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		SoapObject a = new SoapObject();
//		a.addProperty("CategoryCode", "YPZY09");
//		a.addProperty("IsAllRecord", 0);
//		a.addProperty("CurrentPageIndex", 1);
//		a.addProperty("PageSize", 10);

		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
//		rpc.addProperty("categoryCode", "YPZY0101");
//		rpc.addProperty("pageIndex", 1);
//		rpc.addProperty("pageSize", 10);
		rpc.addProperty("theCityName", "北京");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE ht = new HttpTransportSE(URL);

		// AndroidHttpTransport ht = new AndroidHttpTransport(URL);
		ht.debug = true;

		try {
			ht.call(SOAP_ACTION, envelope);

			Log.v("@@@@@@", ht.requestDump);

			detail = (SoapObject)(envelope.getResponse());
			Log.v("@@@@@@", detail.toString());
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// String xmlString = new String(
		// "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
		// + "<GetAudioCategoryList xmlns=\"http://tempuri.org/\">"
		// // + "<SearchCondition>"
		// // + "<CategoryCode>YPZY09</CategoryCode>"
		// // + "<IsAllRecord>0</IsAllRecord>"
		// // + "<CurrentPageIndex>1</CurrentPageIndex>"
		// // + "<PageSize>10</PageSize>" + "</SearchCondition>"+
		// +"</GetAudioCategoryList>");
		//
		// try {
		// byte[] xmlByte = xmlString.getBytes("UTF-8");
		// Log.v("@@@@@@", xmlString);
		//
		// URL url = new URL(URLSTRING);
		// HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// con.setConnectTimeout(5000);
		// con.setDoOutput(true);
		// con.setDoInput(true);
		// con.setUseCaches(false);
		// con.setRequestMethod("POST");
		// // con.setRequestProperty("Connection", "Keep-Alive");
		// con.setRequestProperty("Host", "www.blc.org.cn");
		// // con.setRequestProperty("Charset", "UTF-8");
		// con.setRequestProperty("Content-length",
		// String.valueOf(xmlByte.length));
		// con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		// con.setRequestProperty("SOAPAction", SOAP_ACTION);
		//
		// Log.v("@@@@@@", con.getRequestProperties().toString());
		//
		// con.getOutputStream().write(xmlByte);
		// con.getOutputStream().flush();
		// con.getOutputStream().close();
		// Log.v("@@@@@@", "Job of writing done.");
		//
		// Log.v("@@@@@@", con.getResponseMessage());
		// if (con.getResponseCode() == 200) {
		// Log.v("@@@@@@", "Get response.");
		// InputStream is = con.getInputStream();
		// ByteArrayOutputStream out = new ByteArrayOutputStream();
		// byte[] buf = new byte[1024];
		// int len;
		// while ((len = is.read(buf)) != -1) {
		// out.write(buf, 0, len);
		// }
		// String s = out.toString("UTF-8");
		// Log.v("@@@@@@", s);
		// out.close();
		// }
		//
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.fragment2, null);
		
		return view;
	}
	
}
