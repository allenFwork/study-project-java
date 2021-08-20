package com.study.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 客户端
 */
public class EchoClient {

	public static void main(String[] args) {

		Socket socket = null;
		PrintWriter output = null;
		BufferedReader bufferedReader = null;

		try {

			socket = new Socket("127.0.0.1", 8080);
 			output = new PrintWriter(socket.getOutputStream(), true);
 			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("连接到服务器......");
			System.out.println("请输入消息[输入\"Quit\"]退出：");
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String userInput;

			while ((userInput = stdIn.readLine()) != null) {
				output.println(userInput);
				System.out.println(bufferedReader.readLine());

				if (userInput.equals("Quit")) {
					System.out.println("关闭客户端......");
					output.close();
					bufferedReader.close();
					stdIn.close();
					socket.close();
					System.exit(1);
				}
				System.out.println("请输入消息[输入\"Quit\"]退出：");
			}

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: PallaviÕs MacBook Pro.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for "
					+ "the connection to: PallaviÕs MacBook Pro.");
			System.exit(1);
		}

	}

}
