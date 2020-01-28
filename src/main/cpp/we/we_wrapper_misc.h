#ifndef WE_WRAPPER_MISC_H
#define WE_WRAPPER_MISC_H

#include "src/args.h"
#include "src/dictionary.h"
#include "src/matrix.h"
#include "src/model.h"

/**
 * WE's wrapper misc
 */
namespace WEWrapper {

    struct FastTextPrivateMembers {
        std::shared_ptr <fasttext::Args> args_;
        std::shared_ptr <fasttext::Dictionary> dict_;
        std::shared_ptr <fasttext::Matrix> input_;
        std::shared_ptr <fasttext::Matrix> output_;
        std::shared_ptr <fasttext::Model> model_;
    };
}

#endif
