package io.bpc.gloop;

import io.bpc.gloop.enums.InternalFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class RenderBuffer {
    @Getter
    private final int glId;

    public void setStorage(int width, int height, InternalFormat format) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
