package org.deng.many_algorithms.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.deng.many_algorithms.Service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@RestController
public class codeController {

    @GetMapping(value = "/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        // 设置响应内容类型为图片
        response.setContentType("image/jpeg");

        // 创建验证码图片
        int width = 160;
        int height = 40;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        // 设置背景颜色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 设置字体
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        // 生成随机验证码
        String captcha = generateCaptcha(6);
        request.getSession().setAttribute("captcha", captcha);

        // 绘制验证码
        g2d.setColor(Color.BLACK);
        g2d.drawString(captcha, 20, 30);

        // 释放图形上下文
        g2d.dispose();

        // 将图片写入响应输出流
        ImageIO.write(bufferedImage, "jpeg", response.getOutputStream());
    }

    // 生成随机验证码的方法
    private String generateCaptcha(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder captcha = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            captcha.append(chars.charAt(random.nextInt(chars.length())));
        }
        return captcha.toString();
    }

    @Autowired
    private CodeService codeService;

    @GetMapping(value = "/checkCaptcha")
    public void checkCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage BufferedImagefile= codeService.generateCaptcha(6,150,150) ;

        // 将图片写入响应输出流
        ImageIO.write(BufferedImagefile, "jpeg", response.getOutputStream());

    }
}
