/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.springlets.web.mvc.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import io.springlets.web.mvc.advice.JsonpAdvice;
import io.springlets.web.mvc.advice.StringTrimmerAdvice;
import io.springlets.web.mvc.advice.ValidatorAdvice;

/**
 * Configuration class to register the following beans:
 *
 * * {@link StringTrimmerAdvice}.
 * * {@link JsonpAdvice}.
 * * {@link ValidatorAdvice}.
 *
 * @author Enrique Ruiz at http://www.disid.com[DISID Corporation S.L.]
 * @author Juan Carlos García at http://www.disid.com[DISID Corporation S.L.]
 */
@Configuration
public class SpringletsWebMvcConfiguration {

  @Autowired(required = false)
  private SpringletsWebMvcProperties mvcProperties;
  
  @Autowired(required = false)
  private LocalValidatorFactoryBean localValidator;
  
  /**
   * Create and register a {@link StringTrimmerAdvice} bean configured with {@link #mvcProperties}.
   * 
   * Note that {@link #mvcProperties} is null when the property `springlets.mvc.advices.enabled`
   * is false, in that case this method return null.
   * 
   * By returning null causes that the method {@link AnnotationConfigWebApplicationContext#getBean(Class)}
   * returns null in spite of throwing the {@link BeansException} exception.
   * 
   * @return the StringTrimmerAdvice
   */
  @Bean
  public StringTrimmerAdvice stringTrimmerAdvice() {
    if (mvcProperties == null) {
      return null;
    }
    StringTrimmerAdvice trimmerAdvice = new StringTrimmerAdvice();
    trimmerAdvice.setCharsToDelete(mvcProperties.getAdvices().getTrimeditor().getCharsToDelete());
    trimmerAdvice.setEmptyAsNull(mvcProperties.getAdvices().getTrimeditor().isEmptyAsNull());
    return trimmerAdvice;
  }

  /**
   * Create and register a {@link JsonpAdvice} bean configured with {@link #mvcProperties}.
   * 
   * Note that {@link #mvcProperties} is null when the property `springlets.mvc.advices.enabled`
   * is false, in that case this method return null.
   * 
   * By returning null causes that the method {@link AnnotationConfigWebApplicationContext#getBean(Class)}
   * returns null in spite of throwing the {@link BeansException} exception.
   * 
   * @return the StringTrimmerAdvice
   */
  @Bean
  public JsonpAdvice jsonpAdvice() {
    if (mvcProperties == null) {
      return null;
    }
    JsonpAdvice jsonpAdvice =
        new JsonpAdvice(mvcProperties.getAdvices().getJsonp().getQueryParamNames());
    return jsonpAdvice;
  }
  
  /**
   * Create and register a {@link ValidatorAdvice} bean configured with {@link #localValidator}.
   * 
   * Note that {@link #localValidator} has been annotated with `@Autowired` and 
   * marked as not required so if the {@link LocalValidatorFactoryBean} has not been registered in the 
   * Spring context a new instance of LocalValidatorFactoryBean will be used.
   * 
   * @return the ValidatorAdvice
   */
  @Bean
  public ValidatorAdvice validatorAdvice() {
	  if(localValidator == null){
		  localValidator = new LocalValidatorFactoryBean();
	  }
	  return new ValidatorAdvice(localValidator);
  }
}
