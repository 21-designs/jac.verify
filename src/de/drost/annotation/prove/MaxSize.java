/*
 * This file is part of the application library that simplifies common
 * initialization and helps setting up any java program.
 * 
 * Copyright (C) 2016 Yannick Drost, all rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.drost.annotation.prove;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.drost.AnnotationVerification;
import de.drost.verification.MaxSizeVerification;

/**
 * Assign this annotation to a class field of type {@code Collection},
 * {@code Array} or {@code String} to limit its maximum size.
 * 
 * @author kimschorat
 *
 */
@AnnotationVerification( verification = MaxSizeVerification.class )
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface MaxSize
{
	long value( );
}
