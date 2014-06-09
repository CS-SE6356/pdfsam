/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 09/giu/2014
 * Copyright 2013-2014 by Andrea Vacondio (andrea.vacondio@gmail.com).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pdfsam.split;

import java.util.function.Consumer;

import org.pdfsam.context.DefaultI18nContext;
import org.pdfsam.support.TaskParametersBuildStep;
import org.pdfsam.support.validation.Validators;
import org.pdfsam.ui.commons.RadioButtonDrivenTextField;
import org.pdfsam.ui.support.FXValidationSupport.ValidationState;
import org.sejda.conversion.PageNumbersListAdapter;
import org.sejda.model.parameter.SplitByPagesParameters;

/**
 * Radio button driven text field that can accept a comma separated list of integer numbers.
 * 
 * @author Andrea Vacondio
 *
 */
class SplitAtRadioButtonDrivenTextField extends RadioButtonDrivenTextField implements
        TaskParametersBuildStep<SplitByPagesParameters> {

    public SplitAtRadioButtonDrivenTextField(String radioText) {
        super(radioText);
        setValidator(Validators.newRegexMatchingString("^([0-9]+,?)+$"));
        setErrorMessage(DefaultI18nContext.getInstance().i18n("Invalid page numbers"));
    }

    public void apply(SplitByPagesParameters params, Consumer<String> onError) {
        if (isSelected()) {
            validate();
            if (getValidationState() == ValidationState.INVALID) {
                onError.accept(DefaultI18nContext.getInstance().i18n("Invalid page numbers"));
            } else {
                params.addPages(new PageNumbersListAdapter(getText()).getPageNumbers());
            }
        }
    }

}