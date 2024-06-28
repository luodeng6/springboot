
package org.deng.many_algorithms.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface CodeService {


/**
 * 生成验证码图片并保存到指定文件路径
 *
 * @param length 验证码长度
 * @param width  图片宽度
 * @param height 图片高度
 * @return
 * @throws IOException 如果在保存图片时发生IO错误
 */

     BufferedImage generateCaptcha(int length, int width, int height) throws IOException;
}

