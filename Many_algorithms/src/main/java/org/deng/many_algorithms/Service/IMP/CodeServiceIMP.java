
package org.deng.many_algorithms.Service.IMP;

import org.deng.many_algorithms.Service.CodeService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;


@Service
public class CodeServiceIMP implements CodeService {


    /**
     * 生成验证码图片并保存到指定文件路径
     *
     * @param length 验证码长度
     * @param width  图片宽度
     * @param height 图片高度
     * @throws IOException 如果在保存图片时发生IO错误
     */

    public BufferedImage generateCaptcha(int length, int width, int height) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();

        // 设置背景颜色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // 设置字体
        graphics.setFont(new Font("Arial", Font.BOLD, 40));

        // 生成随机字符串
        String captcha = generateRandomString(length);

        // 绘制字符串
        graphics.setColor(Color.BLACK);
        graphics.drawString(captcha, 25, 35);

        // 画一些干扰线
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            graphics.drawLine(x1, y1, x2, y2);
        }
        // 释放图形上下文
        graphics.dispose();
        // 创建临时文件

        return  bufferedImage;
    }


    /**
     * 生成一个指定长度的随机字符串
     *
     * @param length 字符串长度
     * @return 生成的随机字符串
     */
// 定义字符集合，包括 '1-9'、'a-z' 和 'A-Z'
    private static final String CHARACTERS = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    private static String generateRandomString(int length) {
        // 该代码片段用于生成一个指定长度的随机字符串。
        // 使用 StringBuilder 来逐个字符地构建字符串，每个字符是从指定的字符集合中随机选取的。
        StringBuilder sb = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

}

