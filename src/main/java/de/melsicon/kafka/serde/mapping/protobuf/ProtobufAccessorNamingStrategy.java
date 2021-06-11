package de.melsicon.kafka.serde.mapping.protobuf;

import com.google.auto.service.AutoService;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;
import org.mapstruct.ap.spi.MapStructProcessingEnvironment;

@AutoService(AccessorNamingStrategy.class)
public final class ProtobufAccessorNamingStrategy extends DefaultAccessorNamingStrategy {
  private static final String LIST_SUFFIX = "List";

  private TypeMirror javaUtilMapType;
  private TypeMirror javaUtilListType;
  private TypeMirror javaLangBooleanType;

  private TypeMirror protobufMessageOrBuilderType;
  private TypeMirror protobufByteStringType;
  private TypeMirror protobufUnknownFieldsSetType;

  @SuppressWarnings("nullness:initialization.fields.uninitialized")
  public ProtobufAccessorNamingStrategy() {
    super();
  }

  @Override
  public void init(MapStructProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);

    javaUtilMapType = elementUtils.getTypeElement("java.util.Map").asType();

    javaUtilListType = elementUtils.getTypeElement("java.util.List").asType();

    javaLangBooleanType = elementUtils.getTypeElement("java.lang.Boolean").asType();

    protobufMessageOrBuilderType =
        elementUtils.getTypeElement("com.google.protobuf.MessageOrBuilder").asType();

    protobufByteStringType = elementUtils.getTypeElement("com.google.protobuf.ByteString").asType();

    protobufUnknownFieldsSetType =
        elementUtils.getTypeElement("com.google.protobuf.UnknownFieldSet").asType();
  }

  @Override
  public boolean isGetterMethod(ExecutableElement method) {
    String methodName = method.getSimpleName().toString();
    TypeMirror returnType = method.getReturnType();

    if (methodName.equals("getAllFields")
        && typeUtils.isAssignable(javaUtilMapType, typeUtils.erasure(returnType))) {
      return false;
    }

    if (methodName.startsWith("getMutable")
        && typeUtils.isAssignable(javaUtilMapType, typeUtils.erasure(returnType))) {
      return false;
    }

    if (methodName.startsWith("get")) {
      if (methodName.endsWith("Map")
          && typeUtils.isAssignable(javaUtilMapType, typeUtils.erasure(returnType))) {
        return false;
      }

      if (methodName.endsWith("BuilderList")
          && typeUtils.isAssignable(javaUtilListType, typeUtils.erasure(returnType))) {
        return false;
      }

      return methodName.length() > 3 && returnType.getKind() != TypeKind.VOID;
    }

    if (methodName.startsWith("is")) {
      return methodName.length() > 2
          && (returnType.getKind() == TypeKind.BOOLEAN
              || typeUtils.isSameType(returnType, javaLangBooleanType));
    }

    return false;
  }

  @Override
  public boolean isSetterMethod(ExecutableElement method) {
    String methodName = method.getSimpleName().toString();
    if (!methodName.startsWith("set") || methodName.length() == 3) {
      return false;
    }

    List<? extends VariableElement> parameters = method.getParameters();
    if (parameters.size() != 1) {
      return false;
    }

    TypeMirror parameterType = parameters.get(0).asType();

    if (methodName.equals("setUnknownFields")
        && typeUtils.isSameType(parameterType, protobufUnknownFieldsSetType)) {
      return false;
    }

    if (methodName.endsWith("Bytes")
        && typeUtils.isSameType(parameterType, protobufByteStringType)) {
      return false;
    }

    return !methodName.endsWith("Value") || parameterType.getKind() != TypeKind.INT;
  }

  @Override
  protected boolean isFluentSetter(ExecutableElement method) {
    return super.isFluentSetter(method) && !method.getSimpleName().toString().equals("from");
  }

  @Override
  public String getElementName(ExecutableElement adderMethod) {
    String methodName = super.getElementName(adderMethod);
    Element receiver = adderMethod.getEnclosingElement();
    if (receiver != null
        && !typeUtils.isAssignable(receiver.asType(), protobufMessageOrBuilderType)) {
      return methodName + LIST_SUFFIX;
    }

    return methodName;
  }
}
