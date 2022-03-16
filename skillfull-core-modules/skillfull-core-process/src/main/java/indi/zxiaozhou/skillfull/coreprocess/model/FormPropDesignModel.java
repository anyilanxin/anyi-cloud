// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coreprocess.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

/**
 * 表单信息
 *
 * @author zxiaozhou
 * @date 2022-01-03 11:31
 * @since JDK1.8
 */
public class FormPropDesignModel implements Serializable {
    private static final long serialVersionUID = -8035187351349997365L;

    @Schema(name = "children", title = "设置父级组件的插槽,默认为default.可配合 slot 配置项使用")
    private List<FormPropDesignModel> children;
    
    // ----------基础配置---------start
    @Schema(name = "type", title = "组件的名称")
    private String type;

    @Schema(name = "field", title = "表单组件的字段名称,自定义组件可以不配置")
    private String field;

    @Schema(name = "title", title = "字段的名称,可以不设置")
    private String title;

    @Schema(name = "name", title = "自定义组件的字段名称")
    private String name;

    @Schema(name = "value", title = "表单组件的字段值,自定义组件可以不用设置")
    private Object value;

    @Schema(name = "className", title = "设置FormItem的class")
    private String className;

    @Schema(name = "info", title = "设置组件的提示信息")
    private String info;

    @Schema(name = "nativeCreate", title = "是否原样生成组件,不嵌套的FormItem中")
    private String nativeCreate;

    @Schema(name = "hidden", title = "设置组件是否生成")
    private String hidden;

    @Schema(name = "display", title = "设置组件是否显示")
    private String display;

    @Schema(name = "prefix", title = "设置组件的前缀")
    private String prefix;

    @Schema(name = "suffix", title = "设置组件的后缀")
    private String suffix;
    // ----------基础配置---------end
    // ----------扩展配置---------start
    @Schema(name = "validate", title = "表单组件的验证规则")
    private List<Object> validate;

    @Schema(name = "options", title = "设置radio,select,checkbox等组件option选择项")
    private List<Object> options;

    @Schema(name = "optionsTo", title = "设置options配置项插入的位置. 例如props.data")
    private String optionsTo;

    @Schema(name = "inject", title = "设置事件注入是的自定义数据")
    private Object inject;

    @Schema(name = "effect", title = "设置自定义属性")
    private Object effect;

    @Schema(name = "update", title = "设置update回调,function")
    private Object update;

    @Schema(name = "link", title = "link中配置的字段发生变化后,触发该组件的 update")
    private List<Object> link;

    @Schema(name = "col", title = "设置组件的布局规则")
    private Object col;

    @Schema(name = "wrap", title = "设置组件的FormItem规则")
    private Object wrap;

    @Schema(name = "sync", title = "配置prop.sync,设置props中属性的双向绑定")
    private List<Object> sync;

    @Schema(name = "control", title = "控制其他组件显示")
    private Object control;

    @Schema(name = "emit", title = "使用emit方式触发的事件名,可与emitPrefix参数配合")
    private List<Object> emit;

    @Schema(name = "nativeEmit", title = "使用emit方式触发的原生事件名,可与emitPrefix参数配合")
    private List<Object> nativeEmit;

    @Schema(name = "emitPrefix", title = "自定义组件emit事件的前缀,默认组件field字段")
    private Object emitPrefix;
    // ----------扩展配置---------end

    // ----------通用配置---------start
    @Schema(name = "props", title = "设置组件的props")
    private Object props;

    @Schema(name = "classInfo", title = "设置组件的class,Object|String|Array")
    private Object classInfo;

    @Schema(name = "style", title = "设置组件的style")
    private Object style;

    @Schema(name = "attrs", title = "设置组件普通的 HTML 特性")
    private Object attrs;

    @Schema(name = "domProps", title = "设置组件的DOM属性")
    private Object domProps;

    @Schema(name = "on", title = "设置组件的事件")
    private Object on;

    @Schema(name = "nativeOn", title = "监听组件的原生事件")
    private Object nativeOn;

    @Schema(name = "directives", title = "设置组件的自定义指令")
    private List<Object> directives;

    @Schema(name = "scopedSlots", title = "设置组件的插槽")
    private Object scopedSlots;

    @Schema(name = "slot", title = "设置组件的插槽名称,如果组件是其它组件的子组件，需为插槽指定名称")
    private Object slot;
    // ----------通用配置---------end


}
