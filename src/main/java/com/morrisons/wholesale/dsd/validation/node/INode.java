package com.morrisons.wholesale.dsd.validation.node;

@FunctionalInterface
public interface INode<T> {

	void processNode(T t);
}