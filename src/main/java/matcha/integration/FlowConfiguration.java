package matcha.integration;//package matcha.integration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import matcha.converter.Converter;
//import matcha.converter.Utils;
//import matcha.db.EntityActions;
//import matcha.profile.model.ProfileModel;
//import matcha.properties.*;
//import matcha.response.ResponseError;
//import matcha.response.ResponseOk;
//import matcha.user.model.UserEntity;
//import matcha.validator.ValidatorFactory;
//import org.everit.json.schema.ValidationException;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.integration.config.EnableIntegration;
//import org.springframework.integration.config.EnableIntegrationManagement;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.IntegrationFlows;
//import org.springframework.integration.http.dsl.Http;
//
//
//@Configuration
//@AllArgsConstructor
//@EnableIntegration
//@EnableIntegrationManagement
//@Slf4j
//public class FlowConfiguration {
//
//    private ValidatorFactory validatorFactory;
//    private EntityActions entityActions;
//    private ConfigProperties configProperties;
//
//    @Bean
//    public IntegrationFlow myFlow() {
//        return IntegrationFlows.from(Http.inboundGateway(Gateways.REGISTRATION.getUri())
//                .requestMapping(m -> m.methods(HttpMethod.POST))
//                .crossOrigin(cors -> cors.origin("*"))
//                .requestPayloadType(String.class))
//                .log(Gateways.REGISTRATION.getUri())
//                .transform(o -> validateRegistrationUserBySchema(Schemas.REGISTRY_SCHEMA.getName(), o.toString()))
//                .filter(o -> !(o instanceof ResponseError),
//                        f -> f.discardChannel(Channels.SCHEME_VALIDATION_ERROR.getChannelName()))
//                .transform(entityActions::userRegistry)
//                .get();
//    }
//
//    @Bean
//    public IntegrationFlow loginFlow() {
//        return IntegrationFlows.from(Http.inboundGateway(Gateways.LOGIN.getUri())
//                .requestMapping(m -> m.methods(HttpMethod.POST))
//                .crossOrigin(cors -> cors.origin("*"))
//                .requestPayloadType(String.class))
//                .log(Gateways.LOGIN.getUri())
//                .transform(o -> loginPrepare(Schemas.LOGIN_SCHEMA.getName(), o.toString()))
//                .filter(o -> !(o instanceof ResponseError),
//                        f -> f.discardChannel(Channels.SCHEME_VALIDATION_ERROR.getChannelName()))
////                .transform(this::loginPrepare)
//                .get();
//    }
//
//    @Bean
//    public IntegrationFlow profileUpdateFlow() {
//        return IntegrationFlows.from(Http.inboundGateway(Gateways.PROFILE_UPDATE.getUri())
//                .requestMapping(m -> m.methods(HttpMethod.POST))
//                .crossOrigin(cors -> cors.origin("*"))
//                .requestPayloadType(String.class))
//                .log(Gateways.PROFILE_UPDATE.getUri())
//                .transform(o -> profilePrepare(Schemas.PROFILE_UPDATE_SCHEMA.getName(), o.toString()))
//                .filter(o -> !(o instanceof ResponseError),
//                        f -> f.discardChannel(Channels.SCHEME_VALIDATION_ERROR.getChannelName()))
//                .get();
//    }
//
//    @Bean
//    public IntegrationFlow profileGetFlow() {
//        return IntegrationFlows.from(Http.inboundGateway(Gateways.PROFILE_GET.getUri())
//                .requestMapping(m -> m.methods(HttpMethod.POST))
//                .crossOrigin(cors -> cors.origin("*"))
//                .requestPayloadType(String.class))
//                .log(Gateways.PROFILE_GET.getUri())
//                .transform(o -> profileGetPrepare(Schemas.PROFILE_GET_SCHEMA.getName(), o.toString()))
//                .filter(o -> !(o instanceof ResponseError),
//                        f -> f.discardChannel(Channels.SCHEME_VALIDATION_ERROR.getChannelName()))
//                .get();
//    }
//
//    @Bean
//    public IntegrationFlow schemeValidationErrorChannel() {
//        return IntegrationFlows.from(Channels.SCHEME_VALIDATION_ERROR.getChannelName())
//                .log(Channels.SCHEME_VALIDATION_ERROR.getChannelName())
//                .transform(Object::toString)
//                .get();
//    }
//
////    @Bean
////    public IntegrationFlow outbound() {
////        return IntegrationFlows.from("httpOutRequest")
////                .handle(Http.outboundGateway("http://localhost:8080/foo")
////                        .httpMethod(HttpMethod.POST)
////                        .expectedResponseType(String.class))
////                .get();
////    }
//
//    //                .route(p -> {
////                    OnlyAction onlyAction = ConverterToObject.convertToOnlyAction(p.toString());
////                    if (onlyAction != null)
////                        return onlyAction.getAction();
////                    else
////                        return Channels.NO_ACTION_CHANNEL.getChannelName();
////                })
//
//    private Object validateRegistrationUserBySchema(String schemaName, String json) {
//        Object o = validateOnlyBySchema(schemaName, json);
//        if (o instanceof Boolean) {
//            try {
//                UserEntity user = Converter.convertToUser(json);
//                final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
//                Utils.initRegistryUser(user, node.get("password").asText());
//                user.setActive(configProperties.isUsersDefaultActive());
//                o = user;
//            } catch (Exception e) {
//                log.error("Error. Error mapping json: " + json);
//            }
//        }
//        return o;
//    }
//
////    private Object validateRegistrationUserBySchema(String schemaName, String json) {
////        Object o = validateOnlyBySchema(schemaName, json);
////        if (o instanceof Boolean) {
////            try {
////                User user = Converter.convertToUser(json);
//////                entityActions.
////
////                final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
////                Utils.initRegistryUser(user, node.get("password").asText());
////                o = user;
////            } catch (Exception e) {
////                log.error("Error. Error mapping json: " + json);
////            }
////        }
////        return o;
////    }
//
//    private Object validateOnlyBySchema(String schemaName, String json) {
//        try {
//            validatorFactory.getValidatorMap().get(schemaName).validate(json);
//            return true;
//        } catch (ValidationException e) {
//            String clientMessage;
//            if (e.getCausingExceptions().size() != 0) {
//                clientMessage = Utils.clearValidateMessage(e.getCausingExceptions());
//            } else {
//                if (e.getMessage().matches(StringConstants.validationDelimiter))
//                    clientMessage = e.getMessage().split(StringConstants.validationDelimiter)[1];
//                else
//                    clientMessage = e.getMessage();
//            }
//            StringBuilder sb = new StringBuilder()
//                    .append("Failed schema validate. Schema: ")
//                    .append(schemaName)
//                    .append(" json: ")
//                    .append(json)
//                    .append(" Message: ")
//                    .append(clientMessage);
//            String result = sb.toString();
//            log.warn(result);
//            return new ResponseError("error", clientMessage);
//        }
//    }
//
//    private Object loginPrepare(String schemaName, String json) {
//        Object o = validateOnlyBySchema(schemaName, json);
//        if (o instanceof Boolean) {
//            try {
//                final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
//                UserEntity user = Converter.convertToUser(json);
//                //проверка location. Если там ip, то найти его расположение, иначе - ничего не делать
//                o = entityActions.userLogin(user.getLogin(), user.getPassword(), user.getLocation());
//            } catch (Exception e) {
//                log.error("Error. Error mapping json: " + json);
//            }
//        }
//        return o;
//    }
//
//    private Object profilePrepare(String schemaName, String json) {
//        Object o = validateOnlyBySchema(schemaName, json);
//        if (o instanceof Boolean) {
//            try {
//                UserEntity user = Converter.convertToUser(json);
//                ProfileModel profile = Converter.convertToProfile(json);
//                if (profile.getAvatar() < 0 && profile.getImages() != null && profile.getImages().size() > 0
//                        || profile.getAvatar() >= 0 && profile.getImages() == null
//                        || profile.getAvatar() >= 0 && profile.getImages() != null && profile.getImages().size() == 0) {
//                    log.warn("profilePrepare. Error avatar value [{}]", profile);
//                    return new ResponseError("error", "Ошибка. Не выбран аватар");
//                }
//
//                if (profile.getImages() != null && (profile.getAvatar() >= profile.getImages().size())) {
//                    log.warn("profilePrepare. Error avatar value out of index images [{}]", profile);
//                    return new ResponseError("error", "Ошибка. Указано не существующее значение аватара");
//                }
//                user.getLocation().setActive(true);
//                Object o1 = entityActions.userUpdate(user);
//
//
//                if (o1 instanceof ResponseError)
//                    return o1;
//                profile.setId(user.getProfileId());
//
//                o = entityActions.profileSave(profile);
//
//                if (!(o instanceof ResponseError)) {
//                    o = new ResponseOk("ok", user.getActivationCode(), user.getLogin());
//                }
//            } catch (Exception e) {
//                log.error("Error. Error mapping json: [{}] [{}]", json, e.getMessage());
//                o = new ResponseError("error", e.getMessage());
//            }
//        }
//        return o;
//    }
//
//    private Object profileGetPrepare(String schemaName, String json) {
//        Object o = validateOnlyBySchema(schemaName, json);
//        if (o instanceof Boolean) {
//            try {
//                UserEntity user = Converter.convertToUser(json);
//                o = entityActions.profileGet(user.getLogin());
//            } catch (Exception e) {
//                log.error("Error. Error profileGetPrepare: [{}] [{}]", json, e.getMessage());
//            }
//        }
//        return o;
//    }
//}
