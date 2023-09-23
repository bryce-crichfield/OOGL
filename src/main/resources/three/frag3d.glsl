#version 430 core

in vec3 fragNormal;
in vec2 fragTexCoord;

out vec4 color;

uniform sampler2D sampler;

void main() {
    color = texture(sampler, fragTexCoord);
}