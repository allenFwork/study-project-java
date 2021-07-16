package com.study.servlet;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码的使用
 */
public class ValidateCodeServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 1.创建空白图片
        BufferedImage image = new BufferedImage(100, 30, 10);
        // 2. 获取图片画笔
        Graphics graphics = image.getGraphics();
        // 3. 设置画笔颜色
        Random random = new Random();
        graphics.setColor(new Color(random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255)));
        // 4. 绘制矩形的背景
        graphics.fillRect(0, 0, 100, 30);
        // 5. 调用自定义的方法，获取长度为5的字母数字组合的字符串
        String content = getStringByNumber(5);
        graphics.setColor(new Color(0, 0, 0));
        graphics.setFont(new Font(null, Font.BOLD, 24));
        // 6. 设置颜色字体后，绘制字符串
        graphics.drawString(content, 5, 25);
        // 7. 绘制 8 条 干扰线
        for (int i = 0; i < 8; i++) {
            graphics.setColor(new Color(random.nextInt(255),
                    random.nextInt(255),
                    random.nextInt(255)));
            graphics.drawLine(random.nextInt(100), random.nextInt(30),
                    random.nextInt(100), random.nextInt(30));
        }
        response.setContentType("image/jpeg");
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
        outputStream.close();
    }


    /**
     * 返回指定长度的随机生成的字符串
     */
    public String getStringByNumber(int number) {
        Random random = new Random();
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123465789";
        String result = "";
        for (int i = 0; i < number; i++) {
            result += str.charAt(random.nextInt(str.length()));
        }
        return result;
    }

}
