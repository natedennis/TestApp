CREATE OR REPLACE TRIGGER mon_application_after_insert
AFTER INSERT
   ON p2k_re_applications
   REFERENCING NEW AS newv
   FOR EACH ROW
   
DECLARE

BEGIN
  

   INSERT INTO p2k_am_user_field_values 
   ( muf_id,
     reference_id,
     create_date,
     create_user,
     change_date,
     change_user,
     value )
   VALUES
   ( 331,
     :newv.id,
     current_date,
     'p2k',
     current_date,
     'p2k',
     to_char(sysdate, 'dd-MM-yyyy'));
    
END mon_application_after_insert;
.
run;
/