package br.com.munif.framework.vicente.core.phonetics;

public class PortuguesePhonetic implements PhoneticTranslator {

    private static final String ACCENT_A = "ÁÂÀÃÄ";
    private static final String ACCENT_E = "ÉÈÊẼË";
    private static final String ACCENT_I = "ÍÌÎĨÏ";
    private static final String ACCENT_O = "ÓÔÒÕÖ";
    private static final String ACCENT_U = "ÚÛÙŨÜ";

    public static String removeAccents(String[] split) {
        StringBuilder novaString = new StringBuilder();

        for (int i = 0; i < split.length; i++) {
            if (ACCENT_A.contains(split[i])) {
                split[i] = "A";
            } else if (ACCENT_E.contains(split[i])) {
                split[i] = "E";
            } else if (ACCENT_I.contains(split[i])) {
                split[i] = "I";
            } else if (ACCENT_O.contains(split[i])) {
                split[i] = "O";
            } else if (ACCENT_U.contains(split[i])) {
                split[i] = "U";
            }
            novaString.append(split[i]);
        }

        return novaString.toString();
    }

    public String translate(String str) {
        PortuguesePhonetic phonetic = new PortuguesePhonetic();
        try {
            return phonetic.encode(str.toUpperCase()).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object encode(Object str) {
        return encode((String) str);
    }

    @Override
    public String encode(String str) {
        if (str == null) {
            return null;
        }
        String replaced = str.toUpperCase() + " ";
        return new EncoderLetterByLetter(replaced).encode().trim();
    }

    private class EncoderLetterByLetter {

        private static final String ACCENT = "ÁÂÀÃÄÉÈÊẼËÍÌÎĨÏÓÔÒÕÖÚÛÙŨÜ";
        private static final String VOCALS_O = "OÓÔÒÕÖ";
        private static final String VOCALS_U = "UÚÛÙŨÜ";
        private static final String VOCALS_A = "AÁÂÀÃÄ";
        private static final String VOCALS_AOU = VOCALS_A + VOCALS_O + VOCALS_U;
        private static final String VOCALS_E = "EÉÈÊẼË";
        private static final String VOCALS_I = "IÍÌÎĨÏ";
        private static final String VOCALS_EI = "IÍÌÎĨÏ" + VOCALS_E;
        private static final String VOCALS = VOCALS_AOU + VOCALS_EI;
        private static final String CONSONANTS_MN = "MN";
        private static final String STRONG_CONSONANTS = "BCDFGJKLPQRSTVXZWY";
        private static final String CONSONANTS_WITHOUT_H = STRONG_CONSONANTS + CONSONANTS_MN;

        private int index;
        private String str;
        private StringBuilder buffer;
        private int length;

        public EncoderLetterByLetter(String str) {
            this.index = 0;
            this.str = str;
            this.length = str.length();
            this.buffer = new StringBuilder();
        }

        public String encode() {
            while (this.index < this.length) {
                this.allLetters();
                this.index++;
            }
            String[] split = this.buffer.toString().split("");

            return removeAccents(split);
        }

        private void allLetters() {
            char current = this.str.charAt(this.index);
            if (!treatChar(current)) {
                this.buffer.append(current);
            }
        }

        private boolean treatChar(char current) {
            switch (current) {
                case 'C':
                    return consonantC(current);
                case 'Ç':
                    return consonantCedilha(current);
                case 'G':
                    return consonantG(current);
                case 'H':
                    return consonantH(current);
                case 'L':
                    return consonantL(current);
                case 'M':
                    return consonantM(current);
                case 'N':
                    return consonantN(current);
                case 'P':
                    return consonantP(current);
                case 'Q':
                    return consonantQ(current);
                case 'R':
                    return consonantR(current);
                case 'S':
                    return consonantS(current);
                case 'T':
                    return consonantT(current);
                case 'Y':
                    return consonantY(current);
                case 'W':
                    return consonantW(current);
            }
            if (isA(current)) {
                return vocalAnyA(current);
            }
            if (isE(current)) {
                return vocalAnyE(current);
            }
            if (isO(current)) {
                return vocalAnyO(current);
            }
            if (isU(current)) {
                return vocalAnyU(current);
            }
            return false;
        }

        private boolean consonantC(char current) {
            char next = this.next();
            if (isAOU(next)) {
                this.buffer.append('K');
                return true;
            }
            if (isEI(next)) {
                this.buffer.append('S');
                return true;
            }
            if (next == 'C') {
                if (isAOU(this.next(2))) {
                    this.buffer.append('K');
                    this.index++;
                    return true;
                }
            } else if (next == 'H') {
                this.buffer.append('X');
                this.index++;
                return true;
            }
            return false;
        }

        private boolean consonantCedilha(char current) {
            this.buffer.append('S');
            return true;
        }

        private boolean consonantG(char current) {
            char next = this.next();
            if (next == 'U') {
                if (isEI(this.next(2))) {
                    this.buffer.append('G').append('1');
                    this.index++;
                    return true;
                }
            } else if (next == 'Ü') {
                this.buffer.append('G').append('1').append('U');
                this.index++;
                return true;
            }
            if (isAOU(next)) {
                this.buffer.append('G').append('1');
                return true;
            }
            if (isEI(next)) {
                this.buffer.append('J');
                return true;
            }
            return false;
        }

        private boolean consonantH(char current) {
            return true;
        }

        private boolean consonantL(char current) {
            char next = this.next();
            if (next == 'H') {
                this.buffer.append('L').append('I');
                this.index++;
                return true;
            }

            if (next == 'L') {
                this.buffer.append("L");
                this.index++;
                return true;
            }

            if (CONSONANTS_WITHOUT_H.indexOf(next) != -1 || next == '_' || isSpace(next)) {
                this.buffer.append('U');
                return true;
            }
            return false;
        }

        private boolean consonantM(char current) {
            if (this.isSpace(this.next())) {
                this.buffer.append('N');
                this.index++;
                return true;
            }
            return false;
        }

        private boolean consonantN(char current) {
            if (this.next() == 'H') {
                this.buffer.append('N').append('I');
                this.index++;
                return true;
            }
            return false;
        }

        private boolean consonantP(char current) {
            char next = this.next();
            if (next == 'H') {
                this.buffer.append('F');
                this.index++;
                return true;
            }
            return false;
        }

        private boolean consonantQ(char current) {
            char next = this.next();
            if (isU(next)) {
                char afterNext = this.next(2);
                if (isEI(afterNext)) {
                    if (next == 'Ü') {
                        this.buffer.append('K').append('U');
                        this.index += 1;
                        return true;
                    } else {
                        this.buffer.append('K');
                        this.index += 1;
                        return true;
                    }
                } else if (isU(afterNext)) {
                    this.buffer.append('K').append('U');
                    this.index += 2;
                    return true;
                } else if (isAOU(afterNext)) {
                    this.buffer.append('K').append('U');
                    this.index += 1;
                    return true;
                }
            }
            return false;
        }

        private boolean consonantR(char current) {
            avoidRepeated('R');
            return false;
        }

        private int avoidRepeated(char current) {
            char next = this.next();
            int i = 0;
            while (next == current) {
                next = this.next(++i + 1);
            }
            this.index += i;
            return i;
        }

        private boolean consonantS(char current) {
            if (isVocal(this.prev()) && isVocal(this.next())) {
                this.buffer.append('Z');
                return true;
            }

            if (isSpace(this.next())) {
                this.buffer.append('Z');
                return true;
            }

            avoidRepeated('S');
            char next = this.next();
            if (next == 'H') {
                this.buffer.append('X');
                this.index++;
                return true;
            }
            return false;
        }

        private boolean consonantT(char current) {
            avoidRepeated('T');
            return false;
        }

        //TODO nao cai quando é final 1° nome
        private boolean consonantY(char current) {
            char next = this.next();
            char prev = this.prev();
            char afterNext = this.next(2);

            if (current == 'Y') {
                this.buffer.append('I');
                if (!isVocal(next)) {
                    this.buffer.append(next);
                }

                if (isVocal(next)) {
                    this.buffer.append(next);
                }
                this.index++;
                return true;
            }
            return false;
        }

        private boolean consonantW(char current) {
            char next = this.next();
            char prev = this.prev();

            if (current == 'W') {
                this.buffer.append('V');
                if (!isVocal(next)) {
                    this.buffer.append(next);
                }
                if (isVocal(next)) {
                    this.buffer.append(next);
                }
                if (isSpace(next)) {
                    this.buffer.append(" ");
                }
                this.index++;
                return true;
            }
            return false;
        }

        private boolean vocalAnyA(char current) {
            char next = this.next();
            char next2 = this.next(2);
            if (isO(next) && isSpace(next2)) {
                this.buffer.append('A').append('N');
                this.index++;
                return true;

            }
            if (isU(next)) {
                char afterNext = this.next(2);
                if (isMN(afterNext)) {
                    this.buffer.append('A').append('N');
                    this.index += 2;
                    return true;
                }
            } else if (isMN(next)) {
                char afterNext = this.next(2);
                if (STRONG_CONSONANTS.indexOf(afterNext) != -1 || afterNext == '_') {
                    this.buffer.append('A').append('N');
                    this.index++;
                    return true;
                }
            } else if (current == 'Ã') {
                this.buffer.append('A').append('N');
                if (isO(next)) {
                    this.index++;
                } else if (isU(next) && isMN(this.next(2))) {
                    this.index += 2;
                }
                return true;
            }
            return false;
        }

        private boolean vocalAnyE(char current) {
            if (isA(this.next())) {
                this.buffer.append('E').append('I').append('A');
                this.index++;
                return true;
            }
            return false;
        }

        private boolean vocalAnyO(char current) {
            char afterNext = this.next(2);
            if (afterNext == 'S') {
                char next = this.next();
                if (isEI(next)) {
                    this.buffer.append('O').append('I').append('N').append('S');
                    this.index += 2;
                    return true;
                }
            }
            return false;
        }

        private boolean vocalAnyU(char current) {
            return false;
        }

        private char next() {
            return this.next(1);
        }

        private char next(int salto) {
            return this.index + salto < this.length ? this.str.charAt(this.index + salto) : '_';
        }

        private char prev() {
            return this.prev(1);
        }

        private char prev(int salto) {
            return this.index - salto >= 0 ? this.str.charAt(this.index - salto) : '_';
        }

        private boolean isAOU(char c) {
            return VOCALS_AOU.indexOf(c) != -1;
        }

        private boolean isO(char c) {
            return VOCALS_O.indexOf(c) != -1;
        }

        private boolean isU(char c) {
            return VOCALS_U.indexOf(c) != -1;
        }

        private boolean isEI(char c) {
            return VOCALS_EI.indexOf(c) != -1;
        }

        private boolean isA(char c) {
            return VOCALS_A.indexOf(c) != -1;
        }

        private boolean isVocal(char c) {
            return VOCALS.indexOf(c) != -1;
        }

        private boolean isSpace(char c) {
            return ' ' == c;
        }

        private boolean isE(char c) {
            return VOCALS_E.indexOf(c) != -1;
        }

        private boolean isI(char c) {
            return VOCALS_I.indexOf(c) != -1;
        }

        private boolean isAccentuated(char c) {
            return ACCENT.indexOf(c) != -1;
        }

        private boolean isMN(char c) {
            return CONSONANTS_MN.indexOf(c) != -1;
        }
    }
}
