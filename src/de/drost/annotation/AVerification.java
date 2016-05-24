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
package de.drost.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import de.drost.annotation.prove.Interval;
import de.drost.annotation.prove.Max;
import de.drost.annotation.prove.MaxSize;
import de.drost.annotation.prove.Min;
import de.drost.annotation.prove.MinSize;
import de.drost.annotation.prove.NotNull;

/**
 * Used to verify annotated elements at runtime, such as fields or methods.
 * 
 * @author kimschorat
 *
 */
public class AVerification
{
	/**
	 * Annotation types used to verify the content of class fields.
	 */
	public final static Class<?>[] FIELD_ANNOTATION_TYPES = { Interval.class, Max.class, Min.class, MaxSize.class,
			MinSize.class, NotNull.class };

	/**
	 * Verifies specific annotations and evaluates them to a single
	 * {@link Result} instance.
	 * 
	 * @param o
	 *            The object to get checked for its annotated class fields.
	 * @return A {@code VerificationResult} object.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Result verify( Object o ) throws InstantiationException, IllegalAccessException
	{
//		// Check whether the parameter needs to be verified or if it contains fields to be verified.
//		for(Annotation a : o.getClass( ).getAnnotations( ) )
//			System.out.println(a.annotationType( ));
		
		Class<?> type = o.getClass( );

		Result result = new Result( );

		// Iterating all class fields
		for( Field f : type.getDeclaredFields( ) )
		{
			Result oneResult = verifyField( o, f );
			result.merge( oneResult );
		}

		return result;
	}
	
	@Deprecated
	public static Result verifyMethodParameters( Object o ) throws InstantiationException, IllegalAccessException
	{
		Class<?> type = o.getClass( );

		Result result = new Result( );

		// FIXME No chance to get the parameters value!!!
//		// Iterating all class methods
//		for( Method m : type.getDeclaredMethods( ) )
//		{
//			Parameter[] parameters = m.getParameters( );
//			
//			if(parameters.length == 0)
//				continue;
//			
//			parameters[0].get
//			Annotation[][] annotations = m.getParameterAnnotations();
////			Result oneResult = verifyField( o, f );
////			result.merge( oneResult );
//		}

		return result;
	}

	/**
	 * Evaluates the specified field of the related object.
	 * 
	 * @param o
	 *            The object holding the specified field.
	 * @param f
	 *            The field to be checked.
	 * @return An {@code Evaluation} instance containing information about the
	 *         verification.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Result verifyField( Object o, Field f ) throws InstantiationException, IllegalAccessException
	{
		Result result = new Result( );

		// Get associated annotations
		for( Annotation a : f.getAnnotations( ) )
		{
			// Check for valid field annotations
			for( Class<?> c : FIELD_ANNOTATION_TYPES )
			{
				if( a.annotationType( ).equals( c ) )
				{
					AnnotationVerification verification = c.getAnnotation( AnnotationVerification.class );
					if( verification != null )
					{
						// Verify the field
						Class<? extends Verificator> verificationClass = verification.verifiedBy( );
						Verificator fv = verificationClass.newInstance( );

						boolean wasHidden = false;

						if( !f.isAccessible( ) )
						{
							f.setAccessible( true );

							wasHidden = true;
						}

						String value = null;
						Class<?> t = f.getType( );
						if( t.isPrimitive( ) )
						{
							value = "" + f.get( o );
						}

						// Add a new evaluation
						result.addEvaluation( new Evaluation( f.getName( ), value, a, fv.verify( f.get( o ), a ) ) );

						if( wasHidden )
						{
							f.setAccessible( false );
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Evaluates the specified field of the related object.
	 * 
	 * @param o
	 *            The object holding the specified field.
	 * @param field
	 *            The field to be checked.
	 * @return An {@code Evaluation} instance containing information about the
	 *         verification.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static Result verifyField( Object o, String field )
			throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		return verifyField( o, o.getClass( ).getDeclaredField( field ) );
	}

	
	
	
	
	
	
	
	
	
	/**
	 * Stores multiple {@link Evaluation} instances within one object.
	 * 
	 * @author kimschorat
	 *
	 */
	public static final class Result
	{
		// For the output of toString()
		private static final String CTRL = "\r\n";

		private List<Evaluation> evaluations = new ArrayList<Evaluation>( );

		/**
		 * Creates a new verification result.
		 * 
		 * @param type
		 */
		private Result( )
		{

		}

		/**
		 * Checks whether all the annotated elements has passed its associated
		 * verification.
		 * 
		 * @return {@code true} if and only if all of the content verifications
		 *         has evaluated to {@code true}, otherwise {@code false}.
		 */
		public boolean passedAll( )
		{
//			if( evaluations.isEmpty( ) )
//				throw new IllegalStateException(
//						"There are no results to evaluate. Check whether correct annotations has been used." );

			for( Evaluation e : evaluations )
			{
				if( !e.passed( ) )
					return false;
			}
			return true;
		}

		/**
		 * Returns a list of all the evaluations of the verifications.
		 * 
		 * @return
		 */
		public List<Evaluation> getEvaluations( )
		{
			return evaluations;
		}

		/**
		 * 
		 * @param member
		 * @param a
		 * @param passed
		 * @param message
		 */
		boolean addEvaluation( Evaluation evaluation )
		{
			return evaluations.add( evaluation );
		}

		/**
		 * Merges the evaluations of two separate verification results.
		 * 
		 * @param other
		 * @return
		 */
		boolean merge( Result other )
		{
			return evaluations.addAll( other.getEvaluations( ) );
		}

		@Override
		public String toString( )
		{
			String print = "";
//			String print = "All member validations of the associated object:" + CTRL;

			for( Evaluation e : evaluations )
			{
				print += e.toString( ) + CTRL;
			}

			return print;
		}
	}

	
	
	
	
	
	
	
	/**
	 * This class represents the evaluation of an verification. Only elements
	 * with specified annotations are verified, those related to the content of
	 * the associated element.
	 * 
	 * @author kimschorat
	 *
	 */
	public static final class Evaluation
	{
		final String		member;
		final String		value;
		final Annotation	a;
		final boolean		passed;

		/**
		 * 
		 * @param member
		 * @param annotation
		 * @param passed
		 */
		private Evaluation( String member, String value, Annotation annotation, boolean passed )
		{
			this.member = member;
			this.value = value;
			this.a = annotation;
			this.passed = passed;
		}

		/**
		 * A string holding the associated member name.
		 * @return the associated member name.
		 */
		public String getMember( )
		{
			return member;
		}

		/**
		 * The associated annotations used to validate the related member value.
		 * @return
		 */
		public Annotation getAnnotation( )
		{
			return a;
		}

		/**
		 * Whether the member has passed the evaluation of the related value.
		 * @return
		 */
		public boolean passed( )
		{
			return passed;
		}

		@Override
		public String toString( )
		{
			Class<? extends Annotation> type = a.annotationType( );
			
			String out =  "'" + member + "' " + ( ( value != null ) ? "(" + value + ") " : "" )
					+ ( ( passed ) ? "has been verified" : "has NOT been verified" ) + " by @"
					+ a.annotationType( ).getSimpleName( );
			
			String contraints = "";
			
			// Checks all available annotations
			if(type.equals( Interval.class ))
			{
				contraints = "(" + ((Interval) a).min( ) +", " + ((Interval) a).max( ) + ")";
			}
			else if(type.equals( Max.class ))
			{
				contraints = "(" + ((Max) a).value( ) + ")";
			}
			else if(type.equals( Min.class ))
			{
				contraints = "(" + ((Min) a).value( ) + ")";
			}
			else if(type.equals( MaxSize.class ))
			{
				contraints = "(" + ((MaxSize) a).value( ) + ")";
			}
			else if(type.equals( MinSize.class ))
			{
				contraints = "(" + ((MinSize) a).value( ) + ")";
			}
			
			return out + contraints;
		}
	}
}
