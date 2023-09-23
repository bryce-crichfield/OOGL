#version 430 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 texCoord;

out vec3 fragNormal;
out vec2 fragTexCoord;

uniform mat4 uView;
uniform mat4 uProjection;

void main()
{
    fragNormal = normal;
    fragTexCoord = texCoord;
    gl_Position = uProjection * uView * vec4(position, 1.0);
}
