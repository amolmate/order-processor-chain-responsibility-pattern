package com.morrisons.wholesale.dsd.validation;

public interface INode<T, R> {

	R processNode(T t);
}