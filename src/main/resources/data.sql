 
  INSERT INTO ingredient(ingredient_name,is_active)
   VALUES ('Oil','Y'),('Egg','Y'),('Cheese','Y'),('Spice Masala Dry','Y'),('Potato','Y'),('Salmon','Y'),('Onion','Y'),
('Tomato','Y'),('Ginger and Garlic Paste','Y'),('Salt','Y'),('Paneer','Y'),('Spice Masala Gravy','Y'),('Butter','Y'),('Capsicum','Y'),('Drumstick','Y'),('Dal','Y'),('Chicken','Y')
,('Maida','Y'),('Wheat','Y'),('Oats','Y');

INSERT INTO recipe (recipe_name,is_veg,no_of_servings,is_active)
    VALUES ('Cheese Omlete','N',4,'Y');
   INSERT INTO recipe_instruction (recipe_id,cooking_method,cooking_steps,additional_info) 
   VALUES (1,'Oven','Add oil Egg and cheese and place it in oven','desc');
   INSERT INTO recipe_ingredient (recipe_id,ingredient_id) 
   VALUES (1,1),(1,2),(1,3);
   
    INSERT INTO recipe (recipe_name,is_veg,no_of_servings,is_active)
    VALUES ('Salmon dry fry','N',2,'Y');
   INSERT INTO recipe_instruction (recipe_id,cooking_method,cooking_steps,additional_info) 
   VALUES (2,'Oven','Add oil Marinate fish with spice masala and then put it in oven','desc');
   INSERT INTO recipe_ingredient (recipe_id,ingredient_id) 
   VALUES (2,1),(2,4),(2,6);