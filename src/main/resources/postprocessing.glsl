#version 430 core

in vec2 TexCoord;

out vec4 FragColor;

uniform sampler2D sampler;

void main() {
    FragColor = texture(sampler, TexCoord) * vec4(1.0, 0, 0, 1.0);
}
