# oh my zsh (https://github.com/robbyrussell/oh-my-zsh)
export DEFAULT_USER=$USER
export ZSH=~/.oh-my-zsh
export ZSH_THEME=agnoster
plugins=(git kubectl)
source $ZSH/oh-my-zsh.sh
autoload -U +X bashcompinit && bashcompinit
alias zconf='code ~/.zshrc'

# The Fuck (https://github.com/nvbn/thefuck)
eval $(thefuck --alias)

# Go (https://golang.org/)
export GOPATH=~/go
export PATH=$PATH:$GOPATH/bin

# MySQL (https://www.mysql.com/)
export PATH=$PATH:/usr/local/mysql/bin

# nvm (https://github.com/creationix/nvm)
export NVM_DIR=~/.nvm
[ -s $NVM_DIR/nvm.sh ] && \. $NVM_DIR/nvm.sh
[ -s $NVM_DIR/bash_completion ] && \. $NVM_DIR/bash_completion

# Rust (https://www.rust-lang.org/)
export PATH=$PATH:~/.cargo/bin

# Terraform (https://www.terraform.io/)
complete -o nospace -C /usr/local/bin/terraform terraform

# virtualenvwrapper (https://virtualenvwrapper.readthedocs.io/en/latest/index.html)
export PROJECT_HOME=~/
export VIRTUALENVWRAPPER_PYTHON=/usr/local/bin/python3
export WORKON_HOME=~/.virtualenvs
source /usr/local/bin/virtualenvwrapper.sh

# vscode (https://code.visualstudio.com/)
export PATH=$PATH:/Applications/Visual\ Studio\ Code.app/Contents/Resources/app/bin

# SDKMAN! (https://sdkman.io/) THIS MUST BE AT THE END OF THE FILE FOR SDKMAN TO WORK!!!
export SDKMAN_DIR=~/.sdkman
[[ -s ~/.sdkman/bin/sdkman-init.sh ]] && source ~/.sdkman/bin/sdkman-init.sh
