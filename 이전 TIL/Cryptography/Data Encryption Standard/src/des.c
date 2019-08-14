#include "tables.h"
#include "specification.c"

int main()
{
	char rawText[17];
	char rawKey[17];

	printf("Input a key: ");
	scanf("%s", rawKey);
	printf("Input a plaintext: ");
	scanf("%s", rawText);

	uint plainText[64];
	uint plainKey[64];
	ascii_to_binary(rawText, plainText);
	ascii_to_binary(rawKey, plainKey);

	// Initial Permutation
	uint ipText[64];
	change_bit_position(plainText, ipText, initial_permutation, 64);

	// Parity Drop
	uint parityDroppedKey[56];
	change_bit_position(plainKey, parityDroppedKey, parity_drop, 56);

	// Split 56-bits plainKey into two 28-bits
	uint leftKey[28], rightKey[28];
	for (uint i = 0; i < 28; i++) leftKey[i] = parityDroppedKey[i];
	for (uint i = 28; i < 56; i++) rightKey[i - 28] = parityDroppedKey[i];

	// Split 64-bits plainText into two 32-bits
	uint leftText[32], rightText[32];
	for (uint i = 0; i < 32; i++) leftText[i] = ipText[i];
	for (uint i = 32; i < 64; i++) rightText[i - 32] = ipText[i];

	// Excute 16 main rounds
	des_main_round(leftKey, rightKey, leftText, rightText);

	// Combination
	uint roundedText[64];
	for (uint i = 0; i < 32; i++) roundedText[i] = rightText[i];
	for (uint i = 32; i < 64; i++) roundedText[i] = leftText[i - 32];

	// Final Permutation
	uint cipherText[64];
	change_bit_position(roundedText, cipherText, final_permutation, 64);

	printf("\nCiphertext: ");
	print_binary_to_ascii(cipherText, 64);

	return 0;
}