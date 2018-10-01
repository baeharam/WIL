#ifndef __SPECIFICATION_H_
#define __SPECIFICATION_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h> 
#include "tables.h"

void print_binary_to_ascii(uint* target, uint length);
void swapper(uint** swap1, uint** swap2);
void ascii_to_binary(char* src, uint* dst);
void change_bit_position(uint* src, uint* dst, uint* table, uint length);
void shift_bit_array(uint* target, uint n);
void key_generator(uint* leftKey, uint* rightKey, uint* dst, uint round);
void round_function(uint* leftKey, uint* rightKey, uint* src, uint* dst, uint roundNum);
void des_main_round(uint* leftKey, uint* rightKey, uint* leftText, uint* rightText);



#endif