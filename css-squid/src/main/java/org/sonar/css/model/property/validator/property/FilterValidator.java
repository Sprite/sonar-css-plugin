/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013-2016 Tamas Kende and David RACODON
 * mailto: kende.tamas@gmail.com and david.racodon@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.css.model.property.validator.property;

import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.function.FunctionValidator;
import org.sonar.css.model.value.CssValueElement;

public class FilterValidator implements ValueValidator {

  private static final ValueElementValidator FUNCTION_VALIDATOR = new FunctionValidator(
    "blur", "brightness", "contrast", "drop-shadow", "grayscale", "hue-rotate", "invert", "opacity", "saturate", "sepia",
    "alpha", "basicimage", "blendtrans", "chroma", "compositor", "dropshadow", "emboss", "engrave", "fliph", "flipv",
    "glow", "icmfilter", "light", "maskfilter", "matrix", "motionblur", "redirect", "revealtrans", "shadow", "wave", "xray");

  @Override
  public boolean isValid(Value value) {
    List<CssValueElement> valueElements = value.getValueElements();
    if (ValidatorFactory.getNoneValidator().isValid(valueElements.get(0)) && value.getNumberOfValueElements() == 1) {
      return true;
    }
    for (CssValueElement valueElement : valueElements) {
      if (!FUNCTION_VALIDATOR.isValid(valueElement)
        && !ValidatorFactory.getUriValidator().isValid(valueElement)) {
        return false;
      }
    }
    return true;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "none | [<uri> | <filter-function>]+";
  }

}
