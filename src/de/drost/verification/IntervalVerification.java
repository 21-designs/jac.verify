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
package de.drost.verification;

import de.drost.Verification;
import de.drost.annotation.prove.Interval;

public class IntervalVerification implements Verification<Number, Interval>
{

	@Override
	public boolean verify( Number value, Interval annotation )
	{
		if(value == null)
			return false;
		
		if(value.doubleValue( ) < annotation.min( ) || value.doubleValue( ) > annotation.max( ))
		{
			return false;
		}
		
		if(value.floatValue( ) < annotation.min( ) || value.floatValue( ) > annotation.max( ))
		{
			return false;
		}
		
		if(value.intValue( ) < annotation.min( ) || value.intValue( ) > annotation.max( ))
		{
			return false;
		}
		
		if(value.longValue( ) < annotation.min( ) || value.longValue( ) > annotation.max( ))
		{
			return false;
		}
		
		if(value.byteValue( ) < annotation.min( ) || value.byteValue( ) > annotation.max( ))
		{
			return false;
		}
		
		if(value.shortValue( ) < annotation.min( ) || value.shortValue( ) > annotation.max( ))
		{
			return false;
		}
		
		return true;
	}

}
