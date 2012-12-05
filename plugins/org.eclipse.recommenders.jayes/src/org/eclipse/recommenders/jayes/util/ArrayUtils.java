/**
 * Copyright (c) 2011 Michael Kutschke. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html Contributors:
 * Michael Kutschke - initial API and implementation.
 */
package org.eclipse.recommenders.jayes.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ArrayUtils {

	private ArrayUtils() {

	}

	public static <T extends Number> Object unboxArray(final T[] array) {
		final Class<?> primitiveClass = getPrimitiveClass(array.getClass().getComponentType());
		final Object arr = Array.newInstance(primitiveClass, array.length);
		for (int i = 0; i < array.length; i++) {
			Array.set(arr, i, array[i]);
		}
		return arr;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Number> T[] boxArray(final Object primitiveArray) {
		if (!primitiveArray.getClass().isArray()) {
			throw new IllegalArgumentException("not an array");
		}
		final Class<? extends T> primitiveClass = (Class<? extends T>) getWrapperClass(primitiveArray.getClass().getComponentType());
		final Object arr = Array.newInstance(primitiveClass,
				Array.getLength(primitiveArray));
		for (int i = 0; i < Array.getLength(primitiveArray); i++) {
			Array.set(arr, i, Array.get(primitiveArray, i));
		}
		return (T[]) arr;
	}

	private static Class<?> getWrapperClass(final Class<?> componentType) {
		if (int.class.isAssignableFrom(componentType)) {
			return Integer.class;
		}
		if (double.class.isAssignableFrom(componentType)) {
			return Double.class;
		}
		return null;
	}

	private static Class<?> getPrimitiveClass(final Class<?> componentType) {
		if (Integer.class.isAssignableFrom(componentType)) {
			return int.class;
		}
		if (Double.class.isAssignableFrom(componentType)) {
			return double.class;
		}
		throw new UnsupportedOperationException("Mapping not implemented");
	}

	public static double[] flatten(final Object array) {
		final List<Double> doubles = new ArrayList<Double>();
		flatten(array, doubles);
		final double[] result = new double[doubles.size()];
		int index = 0;
		for (final Double d : doubles) {
			result[index] = d;
			index++;
		}
		return result;
	}

	private static void flatten(final Object array, final List<Double> acc) {
		if (array.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(array); i++) {
				flatten(Array.get(array, i), acc);
			}
		} else if (array.getClass().isAssignableFrom(Double.class)) {
			acc.add((Double) array);
		} else {
			throw new IllegalArgumentException("not a double array!");
		}

	}

	public static List<double[]> unflatten(double[] array, int subsize) {
		if (array.length % subsize != 0) {
			throw new IllegalArgumentException("array.length % subsize != 0");
		}
		List<double[]> doubles = new ArrayList<double[]>();
		for (int i = 0; i < array.length; i += subsize) {
			doubles.add(Arrays.copyOfRange(array, i, i + subsize));
		}
		return doubles;
	}

	public static float[] toFloatArray(double[] array){
		float[] result = new float[array.length];
		for(int i = 0; i< array.length; i++){
			result[i] = (float) array[i];
		}
		return result;
	}
	
	public static double[] toDoubleArray(float[] array){
		double[] result = new double[array.length];
		for(int i = 0; i < array.length; i++){
			result[i] = (double)array[i];
		}
		return result;
	}
	
	public static int[] toIntArray(List<Integer> ints){
		int[] result = new int[ints.size()];
		int i = 0;
		for(Integer j: ints){
			result[i] = j;
			i++;
		}
		return result;
	}

}
