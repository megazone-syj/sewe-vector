#ifndef SE_WRAPPER_MISC_H
#define SE_WRAPPER_MISC_H

#include "src/args.h"
#include "src/dictionary.h"
#include "src/matrix.h"
#include "src/model.h"

/**
 * SE's wrapper misc
 */
namespace SEWrapper {

    struct FastTextPrivateMembers {
        std::shared_ptr <fasttext::Args> args_;
        std::shared_ptr <fasttext::Dictionary> dict_;
        std::shared_ptr <fasttext::Matrix> input_;
        std::shared_ptr <fasttext::Matrix> output_;
        std::shared_ptr <fasttext::Model> model_;
    };
}

#endif
