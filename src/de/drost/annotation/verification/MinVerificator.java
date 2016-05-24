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
package de.drost.annotation.verification;

import de.drost.annotation.Verificator;
import de.drost.annotation.prove.Min;

public class MinVerificator implements Verificator<Number, Min>
{

	@Override
	public boolean verify( Number value, Min annotation )
	{
		if(value == null)
			return false;
		
		if(value.doubleValue( ) < annotation.value( ))
		{
			return false;
		}
		
		if(value.floatValue( ) < annotation.value( ))
		{
			return false;
		}
		
		if(value.intValue( ) < annotation.value( ))
		{
			return false;
		}
		
		if(value.longValue( ) < annotation.value( ))
		{
			return false;
		}
		
		if(value.byteValue( ) < annotation.value( ))
		{
			return false;
		}
		
		if(value.shortValue( ) < annotation.value( ))
		{
			return false;
		}
		
		return true;
	}

}
