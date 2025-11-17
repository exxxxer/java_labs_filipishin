public class Student {
    int id;
    String lastName;
    String firstName;
    String middleName;
    String datebirth;
    String address;
    String phone;
    String faculty;
    int course;
    String group;

    public Student(int id,String ln,String fn,String mn,String db,String a,String p,String fac,int c,String g){
        this.id=id; this.lastName=ln; this.firstName=fn; this.middleName=mn;
        this.datebirth=db; this.address=a; this.phone=p;
        this.faculty=fac; this.course=c; this.group=g;
    }

    public String toString(){
        return id+" "+lastName+" "+firstName+" ("+faculty+"-"+course+")";
    }
}