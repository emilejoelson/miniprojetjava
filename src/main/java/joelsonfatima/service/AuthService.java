package joelsonfatima.service;


import joelsonfatima.dto.request.LoginRequestDto;
import joelsonfatima.dto.request.RegisterDtoRequest;
import joelsonfatima.dto.request.ResetPasswordDtoRequest;

public interface AuthService {
    String login(LoginRequestDto loginDto);

    String register(RegisterDtoRequest registerDtoRequest);

    void resetPassword(ResetPasswordDtoRequest dtoRequest);
}
