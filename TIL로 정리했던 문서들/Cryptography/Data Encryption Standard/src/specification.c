#include "specification.h"

void print_binary_to_ascii(uint* target, uint length)
{
	for (uint i = 0; i < length; i += 4) {
		uint result = 0;

		result += target[i] * 8;
		result += target[i + 1] * 4;
		result += target[i + 2] * 2;
		result += target[i + 3] * 1;

		if (result <= 9) printf("%d", result);
		else printf("%c", result - 10 + 'A');
	}
	printf("\n");
}

void swapper(uint** swap1, uint** swap2)
{
	uint *temp = *swap1;
	*swap1 = *swap2;
	*swap2 = temp;
}

// ASCII --> Hexadecimal --> Binary format
void ascii_to_binary(char* src, uint* dst)
{
	for (int i = 0; i < 16; i++)
		src[i] = src[i] >= 'A' ? src[i] - 'A' + 10 : src[i] - '0';

	for (uint i = 0; i < 16; i++) {
		uint binIndex = 3 + 4 * i;
		for (uint j = 0; j<4; j++) {
			dst[binIndex] = src[i] % 2;
			src[i] /= 2;
			binIndex--;
		}
	}
}

// Change the position of bit using table
void change_bit_position(uint* src, uint* dst, uint* table, uint length)
{
	for (uint i = 0; i < length; i++) {
		dst[i] = src[table[i] - 1];
	}
}

// Shift n-bits of array which has binary numbers
// ※ Fix length as 28
void shift_bit_array(uint* target, uint n)
{
	while (n--) {
		uint pushedBit = target[0];
		for (uint i = 0; i < 28; i++) {
			if (i<27) target[i] = target[i + 1];
			else target[i] = pushedBit;
		}
	}
}

// Generate key at each round
void key_generator(uint* leftKey, uint* rightKey, uint* dst, uint round)
{
	uint shiftNum = 2;
	if (round == 1 || round == 2 || round == 9 || round == 16) shiftNum = 1;

	shift_bit_array(leftKey, shiftNum);
	shift_bit_array(rightKey, shiftNum);

	uint shiftedKey[56];

	// Union left and right into one key : 28 + 28 --> 56
	for (uint i = 0; i < 28; i++) shiftedKey[i] = leftKey[i];
	for (uint i = 28; i < 56; i++) shiftedKey[i] = rightKey[i - 28];

	// Compression
	change_bit_position(shiftedKey, dst, compression_table, 48);
}

// Execute a round using Expansion P-box, XOR, S-Boxes, Straight P-box
void round_function(uint* leftKey, uint* rightKey, uint* src, uint* dst, uint roundNum)
{
	// Expansion P-box
	uint roundText[48];
	change_bit_position(src, roundText, expansion_pbox, 48);

	// Key generation
	uint roundKey[48];
	key_generator(leftKey, rightKey, roundKey, roundNum);

	// XOR operation
	for (uint i = 0; i < 48; i++) roundText[i] ^= roundKey[i];

	// S-Boxes, k is index for sBoxedText array
	uint sBoxedText[32];
	int k = 0;
	for (uint i = 0, sBoxIndex = 0; i < 48; i += 6, sBoxIndex++) {
		uint row = roundText[i] * 2 + roundText[i + 5];
		uint col = roundText[i + 1] * 8 + roundText[i + 2] * 4 + roundText[i + 3] * 2 + roundText[i + 4];

		uint sBoxedValue = sbox[sBoxIndex][col][row];

		for (int j = 3 + k; j >= k; j--) {
			sBoxedText[j] = sBoxedValue % 2;
			sBoxedValue /= 2;
		}
		k += 4;
	}

	// Straight P-box
	change_bit_position(sBoxedText, dst, straight_pbox, 32);
}

// Execute 16 rounds
void des_main_round(uint* leftKey, uint* rightKey, uint* leftText, uint* rightText)
{
	for (uint i = 0; i < 16; i++) {
		uint roundedText[32];
		round_function(leftKey, rightKey, rightText, roundedText, i + 1);
		for (uint j = 0; j < 32; j++) leftText[j] ^= roundedText[j];
		if (i < 15) swapper(&leftText, &rightText);
	}
}