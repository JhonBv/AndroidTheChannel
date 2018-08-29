package uk.co.clysma.jhonbarreiro.dapp.Models;

public class MessagesModel {

    public MessagesModel(){}
    public String FileName;
    public String FileUrl;
    public String Location;
    public String DateAdded;
    public Boolean isApproved;

    //#### GETTERS ######//

    public String GetFileName()
    {
        return FileName;
    }

    public String GetFileUrl()
    {
        return FileUrl;
    }

    public String GetLocation()
    {
        return Location;
    }
    public String GetDateAdded()
    {
        return DateAdded;
    }

    public Boolean IsApproved()
    {
        return isApproved;
    }

    //#### SETTERS ######//
    public void SetFileName(String flName)
    {
        this.FileName = flName;
    }

    public void SetFileUrl(String flurl)
    {
        this.FileUrl = flurl;
    }

    public void SetLocation(String fllocation)
    {
        this.Location = fllocation;
    }
    public void SetDateAdded(String flsentdate)
    {
        this.DateAdded = flsentdate;
    }
    public void SetIsApproved(Boolean isapproved){this.isApproved=isapproved;}
}
