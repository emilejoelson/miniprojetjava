package joelsonfatima.dto.response;


import joelsonfatima.dto.TokenType;

public record JwtAuthResponse (String token, TokenType type){
}
