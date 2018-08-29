package uk.co.clysma.jhonbarreiro.dapp.Models;

public class LoginModel {

    public String _userName;
    public String _password;
    public String _grant_Type;

    //#### GETTERS ######//

    public String GetUserName()
    {
        return _userName;
    }

    public String GetPassword()
    {
        return _password;
    }

    public String GetGrantType()
    {
        return _grant_Type;
    }

    //#### SETTERS ######//
    public void SetUserName(String usrName)
    {
        this._userName = usrName;
    }

    public void SetPassword(String pswrd)
    {
        this._password = pswrd;
    }

    public void SetGrantType(String grantType)
    {
        this._grant_Type = grantType;
    }

}