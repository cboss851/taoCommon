package com.tao.commons.mybatis.plugins;

import java.util.List;
import java.util.function.Predicate;

import com.tao.commons.utils.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class CustomModelPlugin extends PluginAdapter{
	
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
		topLevelClass.addImportedType("javax.validation.constraints.Size");
		
//		topLevelClass.addImportedType("lombok.Builder");
		topLevelClass.addImportedType("lombok.Data");
		
//		topLevelClass.addAnnotation("@Builder");
		topLevelClass.addAnnotation("@Data");
		
		List<String> docs = topLevelClass.getJavaDocLines();
		docs.clear();
		docs.add("/**");
		docs.add(" *");
		docs.add(" * "+introspectedTable.getRemarks());
		docs.add(" *");
		docs.add(" * @author cboss");
		docs.add(" * @Date：2023/3/26");
		docs.add(" **/");
		return true;
	}

	@Override
	public boolean modelFieldGenerated(Field field,
			TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		
		List<String> doclist = field.getJavaDocLines();
		doclist.clear();
		StringBuilder apiModelProperty = new StringBuilder("@ApiModelProperty(value = \"");
		if(StringUtils.isBlank(introspectedColumn.getRemarks()) && introspectedColumn.isIdentity()) {
			apiModelProperty.append("主键\"");
		} else if(StringUtils.isBlank(introspectedColumn.getRemarks())) {
			apiModelProperty.append(introspectedColumn.getJavaProperty()+"\"");
		} else {
			apiModelProperty.append(introspectedColumn.getRemarks()+"\"");
		}
		if(!introspectedColumn.isNullable()) {
			apiModelProperty.append(",required = true)");
		} else {
			apiModelProperty.append(")");
		}
		System.out.println(">name:"+introspectedColumn.getJavaProperty()+",jdbctype:"+introspectedColumn.getJdbcTypeName()+",length:"+introspectedColumn.getLength());
		doclist.add(apiModelProperty.toString());
		
		if (introspectedColumn.getJdbcTypeName().equalsIgnoreCase("date")) {
			topLevelClass.addImportedType("com.fasterxml.jackson.annotation.JsonFormat");
			StringBuilder jsonFormat = new StringBuilder("@JsonFormat(pattern = \"");
			jsonFormat.append("yyyy-MM-dd");
			jsonFormat.append("\",timezone = \"GMT+8\")");
			doclist.add(jsonFormat.toString());
		}
		
		StringBuilder validate = new StringBuilder();
		if(introspectedColumn.getJdbcTypeName().equalsIgnoreCase("varchar")
				||introspectedColumn.getJdbcTypeName().equalsIgnoreCase("char")) {
			validate.append("@Size(max = "+introspectedColumn.getLength()+",message = \""+introspectedColumn.getJavaProperty()+"字段超过字符最大长度限制，最大长度为："+introspectedColumn.getLength()+"\")");
			if(!introspectedColumn.isNullable()) {
				topLevelClass.addImportedType("javax.validation.constraints.NotBlank");
				validate.append("\n\t@NotBlank(message = \""+introspectedColumn.getJavaProperty()+"-"+introspectedColumn.getRemarks()+"字段必填\")");
			}
			doclist.add(validate.toString());
		}
		// 条件匹配，非空数字
		else if (!introspectedColumn.isStringColumn() && !introspectedColumn.isNullable()) {
			System.out.println(">非空数字：["+introspectedColumn.getJavaProperty()+"]暂未处理");
		}
		return true;
	}


	@Override
	public boolean modelGetterMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		
		List<String> doclist = method.getJavaDocLines();
		doclist.clear();
		doclist.add("/**");
		if(StringUtils.isBlank(introspectedColumn.getRemarks())) {
			doclist.add(" * 获取"+introspectedColumn.getJavaProperty());
			doclist.add(" * @return "+introspectedColumn.getJavaProperty());
		} else {
			doclist.add(" * 获取"+introspectedColumn.getRemarks());
			doclist.add(" * @return "+introspectedColumn.getRemarks());
		}
//		doclist.add(" * @mbggenerated");
		doclist.add(" */");
		return false;
	}

	@Override
	public boolean modelSetterMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		
		List<String> doclist = method.getJavaDocLines();
		
		String paranName = method.getParameters().get(0).getName();
		
		doclist.clear();
		doclist.add("/**");
		if(StringUtils.isBlank(introspectedColumn.getRemarks())) {
			doclist.add(" * 设置"+introspectedColumn.getJavaProperty());
		} else {
			doclist.add(" * 设置"+introspectedColumn.getRemarks());
		}
		doclist.add(" * @param "+paranName+" "+introspectedColumn.getRemarks());
//		doclist.add(" * @mbggenerated");
		doclist.add(" */");
		
		return false;
	}

	@Override
	public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		for(VisitableElement ele:element.getElements()) {
			if(ele instanceof XmlElement) {
				XmlElement setElement = (XmlElement)ele;
				setElement.getElements().removeIf(new Predicate<VisitableElement>() {

					@Override
					public boolean test(VisitableElement t) {
						XmlElement ifElement = (XmlElement)t;
						for(Attribute attr:ifElement.getAttributes()) {
							if(attr.getValue().equalsIgnoreCase("updateTime != null")) {
								System.out.println("忽略更新updateTime");
								return true;
							}
						}
						return false;
					}
				});
			}
		}
		return super.sqlMapUpdateByPrimaryKeySelectiveElementGenerated(element, introspectedTable);
	}


	@Override
	public boolean validate(List<String> arg0) {
		return true;
	}

}
