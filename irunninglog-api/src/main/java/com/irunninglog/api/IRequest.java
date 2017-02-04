package com.irunninglog.api;

public interface IRequest<T extends IRequest> {

    T setOffset(int offset);

    int getOffset();

}
