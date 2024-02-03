/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.auth.utils.picture;

import org.dromara.hutool.core.util.ObjUtil;
import org.dromara.hutool.core.util.RandomUtil;
import org.dromara.hutool.swing.img.GraphicsUtil;
import org.dromara.hutool.swing.img.color.ColorUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zxh
 * @date 2023-11-12 13:34
 * @since 1.0.0
 */
public class AnYiCircleCaptcha extends AnYiAbstractCaptcha {
    private static final long serialVersionUID = -7096627300356535494L;
    private Color color;

    /**
     * 构造
     *
     * @param width  图片宽
     * @param height 图片高
     */
    public AnYiCircleCaptcha(final int width, final int height) {
        this(width, height, 5);
    }

    /**
     * 构造
     *
     * @param width     图片宽
     * @param height    图片高
     * @param codeCount 字符个数
     */
    public AnYiCircleCaptcha(final int width, final int height, final int codeCount) {
        this(width, height, codeCount, 15);
    }


    /**
     * 构造
     *
     * @param width          图片宽
     * @param height         图片高
     * @param code           验证码内容
     * @param interfereCount 验证码干扰元素个数
     * @author zxh
     * @date 2023-11-12 15:15:40
     */
    public AnYiCircleCaptcha(final int width, final int height, final String code, Color color, final int interfereCount) {
        super(width, height, code, interfereCount);
        this.color = color;
    }

    /**
     * 构造
     *
     * @param width          图片宽
     * @param height         图片高
     * @param codeCount      字符个数
     * @param interfereCount 验证码干扰元素个数
     * @param color          验证码背景颜色
     * @author zxh
     * @date 2023-11-12 17:31:16
     */
    public AnYiCircleCaptcha(final int width, final int height, final int codeCount, final int interfereCount, Color color) {
        super(width, height, codeCount, interfereCount);
        this.color = color;
    }


    /**
     * 构造
     *
     * @param width          图片宽
     * @param height         图片高
     * @param codeCount      字符个数
     * @param interfereCount 验证码干扰元素个数
     */
    public AnYiCircleCaptcha(final int width, final int height, final int codeCount, final int interfereCount) {
        super(width, height, codeCount, interfereCount);
    }

    @Override
    public Image createImage(final String code) {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        if (Objects.isNull(this.color)) {
            this.color = Color.WHITE;
        }
        final Graphics2D g = GraphicsUtil.createGraphics(image, ObjUtil.defaultIfNull(this.background, this.color));

        // 随机画干扰圈圈
        drawInterfere(g);

        // 画字符串
        drawString(g, code);

        return image;
    }

    // ----------------------------------------------------------------------------------------------------- Private method start

    /**
     * 绘制字符串
     *
     * @param g    {@link Graphics2D}画笔
     * @param code 验证码
     */
    private void drawString(final Graphics2D g, final String code) {
        // 指定透明度
        if (null != this.textAlpha) {
            g.setComposite(this.textAlpha);
        }
        GraphicsUtil.drawStringColourful(g, code, this.font, this.width, this.height);
    }

    /**
     * 画随机干扰
     *
     * @param g {@link Graphics2D}
     */
    private void drawInterfere(final Graphics2D g) {
        final ThreadLocalRandom random = RandomUtil.getRandom();

        for (int i = 0; i < this.interfereCount; i++) {
            g.setColor(ColorUtil.randomColor(random));
            g.drawOval(random.nextInt(width), random.nextInt(height), random.nextInt(height >> 1), random.nextInt(height >> 1));
        }
    }
}
